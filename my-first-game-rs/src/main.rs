use bevy::{prelude::*, utils::HashSet};
use rand::Rng;

fn main() {
    let width = 800_f32;
    let height = width / 12.0 * 9.0;

    App::new()
        .insert_resource(ClearColor(Color::BLACK))
        .insert_resource(GameBounds {
            width: width as i32,
            height: height as i32,
        })
        .add_plugins(DefaultPlugins.set(WindowPlugin {
            window: WindowDescriptor {
                width,
                height,
                title: "My First Game".into(),
                resizable: false,
                ..Default::default()
            },
            ..Default::default()
        }))
        .add_startup_system(spawn_camera)
        .add_startup_system(spawn_player)
        .add_startup_system(spawn_enemies)
        .add_system(bevy::window::close_on_esc)
        .add_system(handle_controls)
        .add_system(move_through_velocity)
        .add_system(bounce_enemies)
        .add_system(keep_in_bounds)
        .add_system(player_enemy_collision)
        .add_system(highlight_collisions)
        .run();
}

fn spawn_camera(mut commands: Commands) {
    commands.spawn(Camera2dBundle::default());
}

fn spawn_player(mut commands: Commands) {
    commands.spawn((
        SpriteBundle {
            sprite: Sprite {
                color: Color::LIME_GREEN,
                custom_size: Some(Vec2::new(32.0, 32.0)),
                ..Default::default()
            },
            transform: Transform::from_xyz(0.0, 0.0, 1.0),
            ..Default::default()
        },
        Bounds(Vec2::new(32.0, 32.0)),
        Player,
        Velocity::default(),
        Controls {
            up: KeyCode::I,
            down: KeyCode::K,
            left: KeyCode::J,
            right: KeyCode::L,
        },
        MovementSpeed(150.0),
        CollisionList::default(),
    ));
}

fn move_through_velocity(mut query: Query<(&mut Transform, &Velocity)>, time: Res<Time>) {
    let delta = time.delta_seconds();

    query.for_each_mut(|(mut transform, velocity)| {
        let scaled_velocity = velocity.0 * delta;
        transform.translation.x += scaled_velocity.x;
        transform.translation.y += scaled_velocity.y;
    });
}

fn highlight_collisions(mut query: Query<(&mut Sprite, &CollisionList)>) {
    for (mut sprite, collision_list) in &mut query {
        if collision_list.is_empty() {
            sprite.color = Color::LIME_GREEN;
        } else {
            sprite.color = Color::AQUAMARINE;
        }
    }
}

fn player_enemy_collision(
    enemies: Query<(Entity, &Transform, &Bounds), With<Enemy>>,
    mut players: Query<(&mut CollisionList, &Transform, &Bounds), With<Player>>,
) {
    for (mut collision_list, player_transform, player_bounds) in &mut players {
        for (enemy, enemy_transform, enemy_bounds) in &enemies {
            let player_rect = Rectangle {
                top_left: player_transform.translation
                    + Vec3::new(
                        -player_bounds.width_half(),
                        player_bounds.height_half(),
                        0.0,
                    ),
                bottom_right: player_transform.translation
                    + Vec3::new(
                        player_bounds.width_half(),
                        -player_bounds.height_half(),
                        0.0,
                    ),
            };
            let enemy_rect = Rectangle {
                top_left: enemy_transform.translation
                    + Vec3::new(-enemy_bounds.width_half(), enemy_bounds.height_half(), 0.0),
                bottom_right: enemy_transform.translation
                    + Vec3::new(enemy_bounds.width_half(), -enemy_bounds.height_half(), 0.0),
            };

            if player_rect.intersects(enemy_rect) {
                collision_list.insert(enemy);
            } else {
                collision_list.remove(&enemy);
            }
        }
    }
}

fn handle_controls(
    mut query: Query<(&mut Velocity, &Controls, &MovementSpeed)>,
    input: Res<Input<KeyCode>>,
) {
    query.for_each_mut(|(mut velocity, controls, movement_speed)| {
        let mut new_velocity = Vec2::default();

        if input.pressed(controls.up) {
            new_velocity.y += 1.0;
        }
        if input.pressed(controls.down) {
            new_velocity.y -= 1.0;
        }
        if input.pressed(controls.right) {
            new_velocity.x += 1.0;
        }
        if input.pressed(controls.left) {
            new_velocity.x -= 1.0;
        }

        velocity.0 = new_velocity.normalize_or_zero() * movement_speed.0;
    });
}

#[derive(Component, Debug, Default, Deref, DerefMut)]
pub struct CollisionList(HashSet<Entity>);

#[derive(Component, Default, Debug)]
pub struct Velocity(Vec2);

#[derive(Resource)]
pub struct GameBounds {
    pub width: i32,
    pub height: i32,
}

impl GameBounds {
    pub fn width_half(&self) -> i32 {
        self.width / 2
    }

    pub fn height_half(&self) -> i32 {
        self.height / 2
    }
}

#[derive(Component)]
struct Player;

#[derive(Component)]
pub struct Controls {
    pub up: KeyCode,
    pub down: KeyCode,
    pub left: KeyCode,
    pub right: KeyCode,
}

#[derive(Component)]
pub struct MovementSpeed(pub f32);

#[derive(Component, Debug)]
pub struct Health(pub f32);

#[derive(Clone, Copy, Debug)]
pub struct Rectangle {
    pub top_left: Vec3,
    pub bottom_right: Vec3,
}

impl Rectangle {
    pub fn intersects(self, other: Self) -> bool {
        if self.top_left.x > other.bottom_right.x || other.top_left.x > self.bottom_right.x {
            return false;
        }

        if self.bottom_right.y > other.top_left.y || other.bottom_right.y > self.top_left.y {
            return false;
        }

        true
    }
}

#[derive(Clone, Copy, Component)]
pub struct Bounds(pub Vec2);

impl Bounds {
    pub fn width_half(self) -> f32 {
        self.0.x / 2.0
    }

    pub fn height_half(self) -> f32 {
        self.0.y / 2.0
    }
}

impl From<Bounds> for Vec3 {
    fn from(value: Bounds) -> Self {
        Vec3::new(value.0.x, value.0.y, 0.0)
    }
}

fn keep_in_bounds(mut query: Query<(&mut Transform, &Bounds)>, game_bounds: Res<GameBounds>) {
    let width_half = game_bounds.width_half() as f32;
    let height_half = game_bounds.height_half() as f32;

    query.for_each_mut(|(mut transform, bounds)| {
        let bounds_half = bounds.0 / 2.0;
        transform.translation.x = transform
            .translation
            .x
            .clamp(-width_half + bounds_half.x, width_half - bounds_half.x);
        transform.translation.y = transform
            .translation
            .y
            .clamp(-height_half + bounds_half.y, height_half - bounds_half.y);
    });
}

#[derive(Component)]
pub struct Enemy;

#[derive(Component)]
pub struct MaxSpeed(pub f32);

#[derive(Component)]
pub struct MinSpeed(pub f32);

fn spawn_enemies(mut commands: Commands, game_bounds: Res<GameBounds>) {
    for _ in 0..4 {
        spawn_enemy(&mut commands, &game_bounds, 16, 90.0);
    }

    for _ in 0..2 {
        spawn_enemy(&mut commands, &game_bounds, 16, 150.0);
    }

    spawn_enemy(&mut commands, &game_bounds, 16, 240.0);
}

fn spawn_enemy(commands: &mut Commands, game_bounds: &Res<GameBounds>, size: i32, max_speed: f32) {
    let min_speed = 80.0;
    let mut rng = rand::thread_rng();
    let size_half = size / 2;
    let bounds = Vec2::new(size as f32, size as f32);
    let position = Vec3::new(
        rng.gen_range((-game_bounds.width / 2 + size_half)..=(game_bounds.width / 2 - size_half))
            as f32,
        rng.gen_range((-game_bounds.height / 2 + size_half)..=(game_bounds.height / 2 - size_half))
            as f32,
        0.0,
    );
    let velocity = Vec2::new(rng.gen_range(-1.0..=1.0), rng.gen_range(-1.0..=1.0) as f32)
        .normalize_or_zero()
        * max_speed
        + min_speed;

    commands
        .spawn(SpriteBundle {
            sprite: Sprite {
                color: Color::RED,
                custom_size: Some(bounds),
                ..Default::default()
            },
            transform: Transform::from_translation(position),
            ..Default::default()
        })
        .insert(Bounds(bounds))
        .insert(Velocity(velocity))
        .insert(Enemy)
        .insert(MinSpeed(min_speed))
        .insert(MaxSpeed(max_speed));
}

fn bounce_enemies(
    mut query: Query<(&mut Velocity, &MinSpeed, &MaxSpeed, &Transform, &Bounds), With<Enemy>>,
    game_bounds: Res<GameBounds>,
) {
    let mut rng = rand::thread_rng();

    query.for_each_mut(|(mut velocity, min_speed, max_speed, transform, bounds)| {
        if transform.translation.x <= -game_bounds.width_half() as f32 + bounds.width_half() {
            velocity.0.x = rng.gen_range(min_speed.0..=max_speed.0);
        }
        if transform.translation.x >= game_bounds.width_half() as f32 - bounds.width_half() {
            velocity.0.x = rng.gen_range(-max_speed.0..=min_speed.0);
        }
        if transform.translation.y <= -game_bounds.height_half() as f32 + bounds.height_half() {
            velocity.0.y = rng.gen_range(min_speed.0..=max_speed.0);
        }
        if transform.translation.y >= game_bounds.height_half() as f32 - bounds.height_half() {
            velocity.0.y = rng.gen_range(-max_speed.0..=min_speed.0);
        }
    });
}

#[cfg(test)]
mod tests {
    mod rectangle {
        use super::super::*;

        #[test]
        fn intersects() {
            let r1 = Rectangle {
                top_left: Vec3::new(0.0, 10.0, 0.0),
                bottom_right: Vec3::new(10.0, 0.0, 0.0),
            };
            let r2 = Rectangle {
                top_left: Vec3::new(5.0, 5.0, 0.0),
                bottom_right: Vec3::new(15.0, 0.0, 0.0),
            };

            assert!(r1.intersects(r2));
        }
    }
}

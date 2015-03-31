package com.interdev.dstrike.screens.game.bullets;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.interdev.dstrike.screens.game.units.Combative;

import java.util.ArrayList;
import java.util.Iterator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class BulletFactory {
    private Stage stage;
    private ArrayList<Bond> bonds = new ArrayList<Bond>();
    private Pool<Bullet> bulletPool;


    public BulletFactory(Stage stg) {
        stage = stg;
        bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                Bullet bullet = new Bullet();
                stage.addActor(bullet);
                return bullet;
            }
        };
    }

    public void act(float deltaTime) {
        for (Bond bond : bonds) {
            bond.act(deltaTime);
        }
    }

    private Bullet getBullet(int type) {
        Bullet bullet = bulletPool.obtain();
        bullet.initTexture(type);
        return bullet;
    }

    private void launchBullet(int type, float startX, float startY, float endX, float endY) {
        Action action = Actions.moveTo(endX, endY, 0.5f);
        final Bullet bullet = getBullet(type);

        bullet.setPosition(startX, startY);

        bullet.setRotation((float) Math.toDegrees(Math.atan2(startY - endY, startX - endX) - Math.PI/2));
        bullet.addAction(sequence(action, new Action() {
            public boolean act(float delta) {
                bulletPool.free(bullet);
                return true;
            }
        }));
        bullet.setVisible(true);
    }


    public void addBond(Combative shooter, Combative target) {
        Bond bond = new Bond(shooter, target);
        bonds.add(bond);
    }

    public void disposeBondByShooter(Combative shooter) {
        Iterator<Bond> it = bonds.iterator();
        while (it.hasNext()) {
            Bond bond = it.next();
            if (bond.shooter == shooter) {
                it.remove();
                return;
            }
        }

    }

    public void disposeBondByTarget(Combative target) {
        Iterator<Bond> it = bonds.iterator();
        while (it.hasNext()) {
            Bond bond = it.next();
            if (bond.target == target) {
                it.remove();
            }
        }
    }

    public class Bond {
        public Combative shooter;
        public Combative target;
        private float timePassed = 999999f;
        private int bulletType;
        private float attackInterval;

        public Bond(Combative shooter, Combative target) {
            this.shooter = shooter;
            this.target = target;
            bulletType = shooter.getBulletType();
            attackInterval = shooter.getAttackInterval() / 1000f; //convert to seconds [libgdx deltas are in seconds]

        }

        public void act(float deltaTime) {
            timePassed += deltaTime;
            if (timePassed >= attackInterval) {
                launchBullet(bulletType, shooter.getX(), shooter.getY(), target.getX(), target.getY());
                timePassed = 0;
            }
        }


    }
}

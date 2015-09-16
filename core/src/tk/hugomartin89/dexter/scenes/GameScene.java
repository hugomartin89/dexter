/**
 * This file is part of El juego de Dexter.
 * Copyright (C) 2014-2015 Hugo Martín <hugomartin89@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tk.hugomartin89.dexter.scenes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.HashSet;
import java.util.Random;
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.DexterGame;
import tk.hugomartin89.dexter.Scene;
import tk.hugomartin89.dexter.SceneManager;
import tk.hugomartin89.dexter.actors.Block;
import tk.hugomartin89.dexter.actors.Dexter;
import tk.hugomartin89.dexter.actors.Pota;

public class GameScene extends Scene
{
    private Music backgroundMusic;
    private Actor backgroundActor;
    private Sound gameOverSound;
    private Sound catchedPotaSound;
    private Sound lostPotaSound;
    private Sound extraLifeSound;
    private Sound blockSound;
    private Sound dexterHurtSound;
    private Random random;
    private boolean playing;
    private HashSet<Actor> potas;
    private HashSet<Actor> blocks;
    private Dexter dexter;
    private float timeForNewItem;
    private float currentTime;
    private float gameSpeed;
    private int score;
    private int lifes;
    private int lostPotasCount;
    private int pointsToNewLife;
    private Label scoreName;
    private Label scoreInfo;
    private Label lifesName;
    private Label lifesInfo;
    private Table hud;

    public void create()
    {
        AssetManager assetManager = DexterGame.getAssetManager();

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        backgroundMusic = assetManager.get("music/game.ogg", Music.class);
        backgroundMusic.setLooping(true);

        backgroundActor = new Actor(assetManager.get("images/game_background.png", Texture.class));

        gameOverSound = assetManager.get("sounds/game_over.ogg", Sound.class);
        catchedPotaSound = assetManager.get("sounds/catch_pota.ogg", Sound.class);
        lostPotaSound = assetManager.get("sounds/lost_pota.ogg", Sound.class);
        extraLifeSound = assetManager.get("sounds/extra_life.ogg", Sound.class);
        blockSound = assetManager.get("sounds/block.ogg", Sound.class);
        dexterHurtSound = assetManager.get("sounds/dexter_hurts.ogg", Sound.class);

        random = new Random();

        potas = new HashSet<Actor>();
        blocks = new HashSet<Actor>();

        dexter = new Dexter();

        scoreName = new Label("Puntaje: ", skin);
        scoreName.setColor(Color.ORANGE);

        scoreInfo = new Label("0", skin);
        scoreInfo.setColor(Color.RED);

        lifesName = new Label("Vidas: ", skin);
        lifesName.setColor(Color.ORANGE);

        lifesInfo = new Label(String.valueOf(this.lifes), skin);
        lifesInfo.setColor(Color.RED);

        hud = new Table(skin);
        hud.add(scoreName).right();
        hud.add(scoreInfo).center();
        hud.row();
        hud.add(lifesName).left();
        hud.add(lifesInfo).right();
        hud.setPosition(65.0F, 450.0F);
        hud.toFront();

        addActor(backgroundActor);
        addActor(dexter);
        addActor(hud);
    }

    public void initialize()
    {
        potas.clear();
        blocks.clear();

        playing = true;

        timeForNewItem = 2.0F;
        currentTime = 0.0F;
        gameSpeed = 2.0F;
        score = 0;
        lifes = 3;
        lostPotasCount = 0;
        pointsToNewLife = 500;

        dexter.setState(Dexter.State.STAND);
        dexter.setPosition(320.0F, 15.0F);

        addAction(Actions.moveTo(0.0F, getHeight(), 0.0F));
        act();

        updateHUD();
    }

    public void leave(Scene to)
    {
        if (to == DexterGame.getSceneManager().getScene("MainMenu")) {
            backgroundMusic.pause();
        }
    }

    public void resume(Scene from)
    {
        backgroundMusic.play();

        addAction(Actions.moveTo(0.0F, 0.0F, 0.15F));
    }

    private void finish()
    {
        final SceneManager sceneManager = DexterGame.getSceneManager();

        addAction(
            Actions.sequence(
                Actions.run(new Runnable() {
                    public void run() {
                        backgroundMusic.stop();

                        clearItems();
                        updateHUD();

                        gameOverSound.play();
                    }
                }),

                Actions.delay(3.0F),

                Actions.moveTo(getWidth(), 0.0F, 0.15F),

                Actions.run(new Runnable() {
                    public void run() {
                        sceneManager.setActiveScene("Info");
                    }
                })
            )
        );
    }

    public void reset()
    {
        initialize();
    }

    public int getScore()
    {
        return score;
    }

    public void act(float delta)
    {
        super.act(delta);

        if (playing)
        {
            currentTime += delta;

            if (currentTime >= timeForNewItem) {
                Actor actor;
                if (random.nextBoolean()) {
                    actor = new Pota(2.0F + random.nextFloat() * gameSpeed);
                    potas.add(actor);
                }
                else {
                    actor = new Block(2.0F + random.nextFloat() * gameSpeed);
                    blocks.add(actor);
                }

                actor.setX(random.nextFloat() * (getWidth() - actor.getWidth()));

                addActor(actor);

                timeForNewItem = (currentTime + 3.0F / gameSpeed + random.nextFloat() * 2.0F / gameSpeed);
            }
        }
    }

    public void act()
    {
        super.act();

        if (playing) {
            HashSet<Actor> toRemove = new HashSet<Actor>();

            for (Actor pota : potas) {
                if (dexter.collectPota((Pota)pota)) {
                    catchedPota((Pota)pota);
                    toRemove.add(pota);
                }
                else if (pota.getY() <= 10.0F) {
                    lostPota((Pota)pota);
                    toRemove.add(pota);
                }
            }

            for (Actor block : blocks) {
                if (this.dexter.collectBlock((Block)block)) {
                    catchedBlock((Block)block);

                    toRemove.add(block);
                }
                else if (block.getY() <= 10.0F) {
                    blockSound.play();
                    block.remove();
                    toRemove.add(block);
                }
            }

            potas.removeAll(toRemove);
            blocks.removeAll(toRemove);
        }
    }

    public void lostPota(Pota pota)
    {
        lostPotaSound.play();

        pota.remove();

        score -= 25;

        lostPotasCount += 1;

        if (lostPotasCount >= 3) {
            lifes -= 1;

            lostPotasCount = 0;
        }

        updateHUD();
    }

    public void catchedPota(Pota pota)
    {
        catchedPotaSound.play();

        pota.remove();

        score += 50;

        if (this.score >= this.pointsToNewLife) {
            extraLifeSound.play();

            lifes += 1;

            pointsToNewLife += 500;

            gameSpeed += 1.0F;

            dexter.setSpeed(this.gameSpeed + 1.0F);
        }

        updateHUD();
    }

    public void catchedBlock(Block block)
    {
        dexterHurtSound.play();

        block.remove();

        score -= 50;

        lifes -= 1;

        updateHUD();
    }

    private void updateHUD()
    {
        if (this.playing)
        {
            playing = (this.lifes >= 0);

            if (this.score < 0) {
                score = 0;
            }

            if (this.lifes < 0) {
                lifes = 0;
            }

            scoreInfo.setText(String.valueOf(score));
            lifesInfo.setText(String.valueOf(lifes));

            if (!playing) {
                finish();
            }
        }
    }

    private void clearItems()
    {
        for (Actor pota : potas) {
            pota.remove();
        }

        for (Actor block : blocks) {
            block.remove();
        }

        potas.clear();
        blocks.clear();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector2 vec = screenToStageCoordinates(new Vector2(screenX, screenY));

        if (vec.x <= getWidth() / 2.0F) {
            dexter.setState(Dexter.State.MOVE_LEFT);
        }
        else if (vec.x > getWidth() / 2.0F) {
            dexter.setState(Dexter.State.MOVE_RIGHT);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Vector2 vec = screenToStageCoordinates(new Vector2(screenX, screenY));

        switch (dexter.getState()) {
            case MOVE_LEFT: {
                if (vec.x <= getWidth() / 2.0F) {
                    this.dexter.setState(Dexter.State.STAND);
                }
            }
            break;

            case MOVE_RIGHT: {
                if (vec.x > getWidth() / 2.0F) {
                    this.dexter.setState(Dexter.State.STAND);
                }
            }
            break;

            default: {
            }
            break;
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    public boolean keyDown(int keyCode)
    {
        if ((keyCode == 21) || (keyCode == 29)) {
            dexter.setState(Dexter.State.MOVE_LEFT);
        }
        else if ((keyCode == 22) || (keyCode == 32)) {
            dexter.setState(Dexter.State.MOVE_RIGHT);
        }
        else if ((keyCode == 4) || (keyCode == 131)) {
            addAction(
                Actions.sequence(
                    Actions.moveTo(0.0F, getHeight(), 0.15F),
                    Actions.run(new Runnable() {
                        public void run() {
                            DexterGame.getSceneManager().setActiveScene("MainMenu");
                        }
                    })
                )
            );
        }

        return super.keyDown(keyCode);
    }

    public boolean keyUp(int keyCode)
    {
        switch (dexter.getState()) {
            case MOVE_LEFT: {
                if ((keyCode == 21) || (keyCode == 29)) {
                    dexter.setState(Dexter.State.STAND);
                }
            }
            break;

            case MOVE_RIGHT: {
                if ((keyCode == 22) || (keyCode == 32)) {
                    dexter.setState(Dexter.State.STAND);
                }
            }
            break;

            default: {
            }
            break;
        }

        return super.keyUp(keyCode);
    }
}

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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.DexterGame;
import tk.hugomartin89.dexter.Scene;
import tk.hugomartin89.dexter.SceneManager;

public class MainMenuScene extends Scene
{
    private Music backgroundMusic;
    private Actor backgroundActor;
    private Label title;
    private TextButton playButton;
    private TextButton helpButton;
    private TextButton exitButton;
    private Table menu;

    public void create()
    {
        AssetManager assetManager = DexterGame.getAssetManager();

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        backgroundMusic = assetManager.get("music/menu.ogg", Music.class);
        backgroundMusic.setLooping(true);

        backgroundActor = new Actor(assetManager.get("images/menu_background.png", Texture.class));

        title = new Label("El juego de Dexter", skin);

        playButton = new TextButton("Jugar", skin);
        helpButton = new TextButton("Ayuda", skin);
        exitButton = new TextButton("Salir", skin);

        menu = new Table(assetManager.get("skin/uiskin.json", Skin.class));
        menu.setWidth(150.0F);
        menu.add(playButton).center().fill().pad(2.0F).width(150.0F).height(50.0F);
        menu.row();
        menu.add(helpButton).center().fill().pad(2.0F).height(50.0F);

        menu.row();
        menu.add(exitButton).center().fill().pad(2.0F).height(50.0F);

        addActor(backgroundActor);
        addActor(menu);
        addActor(title);
    }

    public void initialize()
    {
        final SceneManager sceneManager = DexterGame.getSceneManager();

        backgroundActor.setOrigin(backgroundActor.getWidth() / 2.0F, backgroundActor.getHeight() / 2.0F);
        backgroundActor.setPosition(getWidth() / 2.0F, getHeight() / 2.0F);

        title.setPosition(250.0F, getHeight());

        playButton.addCaptureListener(new ChangeListener() {
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                addAction(
                     Actions.sequence(
                        Actions.moveTo(0.0F, -getHeight(), 0.15F),
                        Actions.run(new Runnable() {
                            public void run() {
                                sceneManager.setActiveScene("Game");
                            }
                        })
                    )
                );
            }
        });

        helpButton.addCaptureListener(new ChangeListener() {
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                addAction(
                     Actions.sequence(
                      Actions.moveTo(-getWidth(), 0.0F, 0.15F),
                      Actions.run(new Runnable() {
                          public void run() {
                              sceneManager.setActiveScene("Help");
                          }
                      })
                     )
                );
            }
        });

        exitButton.addCaptureListener(new ChangeListener() {
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Gdx.app.exit();
            }
        });

        menu.setPosition(getWidth(), getHeight());
    }

    public void leave(Scene to)
    {
        if (to == DexterGame.getSceneManager().getScene("Game")) {
            backgroundMusic.stop();
        }
    }

    public void resume(Scene from)
    {
        SceneManager sceneManager = DexterGame.getSceneManager();

        if (from == sceneManager.getScene("SplashScreen")) {
            backgroundActor.addAction
            (
                Actions.sequence
                (
                    Actions.scaleTo(0.0F, 0.0F, 0.0F),
                    Actions.fadeOut(0.0F),
                    Actions.parallel
                    (
                        Actions.fadeIn(1.0F),
                        Actions.rotateBy(720.0F, 0.5F),
                        Actions.scaleTo(1.0F, 1.0F, 0.5F)
                    ),

                    Actions.run(new Runnable() {
                        public void run() {
                            addTitle();
                            addMenu();
                      }
                    })
                 )
            );
        }
        else if (from == sceneManager.getScene("Game"))
        {
            backgroundMusic.play();

            playButton.setText("Continuar");

            addAction
            (
                Actions.sequence
                (
                    Actions.moveTo(0.0F, -getHeight(), 0.0F),
                    Actions.moveTo(0.0F, 0.0F, 0.15F)
                )
            );
        }
        else if (from == sceneManager.getScene("Help"))
        {
            addAction(
                Actions.sequence(
                  Actions.moveTo(-getWidth(), 0.0F, 0.0F),
                  Actions.moveTo(0.0F, 0.0F, 0.15F)
                )
            );
        }
        else if (from == sceneManager.getScene("Info"))
        {
            backgroundMusic.play();
            playButton.setText("Jugar");
            addAction(
                Actions.sequence(
                    Actions.moveTo(getWidth(), 0.0F, 0.0F),
                    Actions.moveTo(0.0F, 0.0F, 0.15F)
                )
            );
        }
    }

    protected void addTitle()
    {
        title.clearActions();
        title.addAction(
            Actions.sequence(
                Actions.delay(0.5F),
                Actions.moveTo(250.0F, getHeight(), 0.0F),
                Actions.moveTo(250.0F, 400.0F, 0.25F)
            )
        );
        title.act(0.0F);
    }

    protected void addMenu()
    {
        menu.clearActions();
        menu.addAction(
            Actions.sequence(
                Actions.moveTo(450.0F, getHeight(), 0.0F),
                Actions.moveTo(450.0F, 200.0F, 0.5F)
            )
        );
        menu.act(0.0F);
    }

    public boolean keyDown(int keyCode)
    {
        if ((keyCode == 4) || (keyCode == 131)) {
            Gdx.app.exit();
        }

        return super.keyDown(keyCode);
    }
}

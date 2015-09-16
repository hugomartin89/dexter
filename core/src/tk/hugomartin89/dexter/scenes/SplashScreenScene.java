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
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.DexterGame;
import tk.hugomartin89.dexter.Scene;
import tk.hugomartin89.dexter.SceneManager;

public class SplashScreenScene extends Scene
{
    private Actor libGdxLogo;
    private Actor gradleLogo;
    private Actor vimLogo;

    public void create()
    {
        AssetManager assetManager = DexterGame.getAssetManager();

        libGdxLogo = new Actor(assetManager.get("images/libgdx_logo.png", Texture.class));
        gradleLogo = new Actor(assetManager.get("images/gradle_logo.png", Texture.class));
        vimLogo = new Actor(assetManager.get("images/vim_logo.png", Texture.class));

        addActor(libGdxLogo);
        addActor(gradleLogo);
        addActor(vimLogo);
    }

    public void initialize()
    {
        final AssetManager assetManager = DexterGame.getAssetManager();
        final SceneManager sceneManager = DexterGame.getSceneManager();

        libGdxLogo.setOrigin(libGdxLogo.getWidth() / 2.0f, libGdxLogo.getHeight() / 2.0f);
        libGdxLogo.setPosition(getWidth() / 2.0f, getHeight() / 2.0f + libGdxLogo.getHeight());

        gradleLogo.setOrigin(gradleLogo.getWidth() / 2.0f, gradleLogo.getHeight() / 2.0f);
        gradleLogo.setPosition(getWidth() / 2.0f - gradleLogo.getWidth() / 2.0f + 50.0f, getHeight() / 2.0f - libGdxLogo.getHeight());

        vimLogo.setOrigin(vimLogo.getWidth() / 2.0f, vimLogo.getHeight() / 2.0f);
        vimLogo.setPosition(getWidth() / 2.0f + gradleLogo.getWidth() / 2.0f + 20.0f, getHeight() / 2.0f - libGdxLogo.getHeight());

        Gdx.gl.glClearColor(0.45f, 0.65f, 1.0f, 0.0f);

        addAction(
            Actions.sequence(
                Actions.fadeOut(0.0f),
                Actions.fadeIn(1.0f),
                Actions.delay(2.5f),
                Actions.run(new Runnable() {
                    public void run() {
                        assetManager.get("music/menu.ogg", Music.class).play();
                    }
                }),
                Actions.fadeOut(1.0f),
                Actions.run(new Runnable() {
                    public void run() {
                        sceneManager.setActiveScene("MainMenu");

                        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                })
            )
        );
    }

    public void leave(Scene to)
    {
    }

    public void resume(Scene from)
    {
    }

    public boolean keyDown(int keyCode)
    {
        if ((keyCode == 4) || (keyCode == 131)) {
            Gdx.app.exit();
        }

        return super.keyDown(keyCode);
    }
}

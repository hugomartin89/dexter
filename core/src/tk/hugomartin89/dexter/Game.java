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

package tk.hugomartin89.dexter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Game implements ApplicationListener
{
    private static AssetManager assetManager = null;
    private static SceneManager sceneManager = null;
    private static SpriteBatch spriteBatch = null;

    protected abstract void load();
    protected abstract void setupScenes();
    public abstract void pause();
    public abstract void resume();

    public void create()
    {
        assetManager = new AssetManager();
        sceneManager = new SceneManager();
        spriteBatch = new SpriteBatch();

        Gdx.input.setCatchBackKey(true);

        load();

        getAssetManager().finishLoading();

        setupScenes();
    }

    public void resize(int width, int height)
    {
        Scene activeScene = sceneManager.getActiveScene();

        activeScene.getViewport().update(width, height);
    }

    public void render()
    {
        Scene activeScene = sceneManager.getActiveScene();

        Gdx.gl.glClear(16384);

        activeScene.act();
        activeScene.draw();
    }

    public void dispose()
    {
        spriteBatch.dispose();
        sceneManager.dispose();
        assetManager.dispose();
    }

    public static AssetManager getAssetManager()
    {
        return assetManager;
    }

    public static SceneManager getSceneManager()
    {
        return sceneManager;
    }

    public static SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }
}

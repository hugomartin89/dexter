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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import tk.hugomartin89.dexter.scenes.GameScene;
import tk.hugomartin89.dexter.scenes.HelpScene;
import tk.hugomartin89.dexter.scenes.InfoScene;
import tk.hugomartin89.dexter.scenes.MainMenuScene;
import tk.hugomartin89.dexter.scenes.SplashScreenScene;

public class DexterGame extends Game
{
    protected void load()
    {
        AssetManager assetManager = getAssetManager();

        TextureParameter textureParameter = new TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.minFilter = Texture.TextureFilter.Linear;

        assetManager.load("music/menu.ogg", Music.class);
        assetManager.load("music/game.ogg", Music.class);

        assetManager.load("sounds/block.ogg", Sound.class);
        assetManager.load("sounds/catch_pota.ogg", Sound.class);
        assetManager.load("sounds/dexter_hurts.ogg", Sound.class);
        assetManager.load("sounds/extra_life.ogg", Sound.class);
        assetManager.load("sounds/game_over.ogg", Sound.class);
        assetManager.load("sounds/lost_pota.ogg", Sound.class);

        assetManager.load("images/libgdx_logo.png", Texture.class, textureParameter);
        assetManager.load("images/gradle_logo.png", Texture.class, textureParameter);
        assetManager.load("images/vim_logo.png", Texture.class, textureParameter);

        assetManager.load("images/menu_background.png", Texture.class, textureParameter);
        assetManager.load("images/help_background.png", Texture.class, textureParameter);
        assetManager.load("images/info_background.png", Texture.class, textureParameter);
        assetManager.load("images/game_background.png", Texture.class, textureParameter);

        assetManager.load("images/dexter_stand.png", Texture.class, textureParameter);
        assetManager.load("images/dexter_catch.png", Texture.class, textureParameter);
        assetManager.load("images/dexter_running.png", Texture.class, textureParameter);
        assetManager.load("images/dexter_angry.png", Texture.class, textureParameter);

        assetManager.load("images/pota.png", Texture.class, textureParameter);
        assetManager.load("images/block.png", Texture.class, textureParameter);

        assetManager.load("skin/uiskin.json", Skin.class);
    }

    protected void setupScenes()
    {
        AssetManager assetManager = getAssetManager();
        SceneManager sceneManager = getSceneManager();

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        skin.getFont("default-font")
            .getRegion()
            .getTexture()
            .setFilter(TextureFilter.Linear, TextureFilter.Linear);

        skin.getFont("default-font").setScale(1.25F);

        sceneManager.addScene("SplashScreen", new SplashScreenScene());
        sceneManager.addScene("MainMenu", new MainMenuScene());
        sceneManager.addScene("Game", new GameScene());
        sceneManager.addScene("Help", new HelpScene());
        sceneManager.addScene("Info", new InfoScene());
    }

    public void pause()
    {
    }

    public void resume()
    {
    }

    public void render()
    {
        super.render();
    }
}

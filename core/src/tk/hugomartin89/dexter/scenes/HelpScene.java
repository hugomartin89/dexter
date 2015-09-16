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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.DexterGame;
import tk.hugomartin89.dexter.Scene;
import tk.hugomartin89.dexter.SceneManager;

public class HelpScene extends Scene
{
    private String message;
    private Actor backgroundActor;
    private Label infoLabel;

    public void create()
    {
        AssetManager assetManager = DexterGame.getAssetManager();

        switch (Gdx.app.getType()) {
            case Android: {
                message = "Este es el juego de Dexter.\nEl juego es muy simple, solo debes recolectar\nlos frascos de laboratorio e impedir que toquen\nel suelo. Mueves al personaje con 'A','D'\no presionando la pantalla en los lados.\nPuedes abandonar el juego\nen cualquier momento\npresionando 'BACK'\n\nEvita los bloques, y...\n\nBuena Suerte!!";
            }
            break;

            default: {
                message = "Este es el juego de Dexter.\nEl juego es muy simple, solo debes recolectar\nlos frascos de laboratorio e impedir que toquen\nel suelo. Mueves al personaje con 'A','D'\no con las flechas de direccion '<-' y '->'\nPuedes abandonar el juego\nen cualquier momento\npresionando 'ESC'\n\nEvita los bloques, y...\n\nBuena Suerte";
            }
            break;
        }

        backgroundActor = new Actor(assetManager.get("images/help_background.png", Texture.class));

        infoLabel = new Label(message, assetManager.get("skin/uiskin.json", Skin.class));

        addActor(backgroundActor);
        addActor(infoLabel);
    }

    public void initialize()
    {
        infoLabel.setPosition(50.0F, 100.0F);

        addAction(
            Actions.moveTo(getWidth(), 0.0F, 0.0F)
        );

        act();
    }

    public void leave(Scene to)
    {
    }

    public void resume(Scene from)
    {
        addAction(Actions.moveTo(0.0F, 0.0F, 0.15F));
    }

    public boolean keyDown(int keyCode)
    {
        final SceneManager sceneManager = DexterGame.getSceneManager();

        if ((keyCode == 4) || (keyCode == 131)) {
            addAction(
                Actions.sequence(
                    Actions.moveTo(getWidth(), 0.0F, 0.15F),

                    Actions.run(new Runnable() {
                        public void run() {
                            sceneManager.setActiveScene("MainMenu");
                        }
                    })
                )
            );
        }

        return super.keyDown(keyCode);
    }
}

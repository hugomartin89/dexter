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

package tk.hugomartin89.dexter.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.DexterGame;

public class Item extends Actor
{
    private final float speed;

    public Item(String textureName, float speed)
    {
        AssetManager assetManager = DexterGame.getAssetManager();
        this.speed = speed;

        setTexture(assetManager.get(textureName, Texture.class));
        setPosition(320.0F, 500.0F);
    }


    public void act(float delta)
    {
        super.act(delta);

        setY(getY() - speed);
    }
}

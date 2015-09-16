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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor
{
    private boolean flipX;
    private boolean flipY;
    private int sourceX;
    private int sourceY;
    private int sourceWidth;
    private int sourceHeight;
    private Texture texture;

    public Actor()
    {
        this.flipX = false;
        this.flipY = false;
        this.texture = null;
    }

    public Actor(Texture texture)
    {
        this();

        setTexture(texture);
    }

    public void draw(Batch batch, float parentAlpha)
    {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(getTexture(),
            getX() - getOriginX(), getY() - getOriginY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation(),
            getSourceX(), getSourceY(),
            getSourceWidth(), getSourceHeight(),
            getFlipX(), getFlipY()
        );
    }

    public void setFlip(boolean flipX, boolean flipY)
    {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public void setFlipX(boolean flip)
    {
        this.flipX = flip;
    }

    public void setFlipY(boolean flip)
    {
        this.flipY = flip;
    }

    public boolean getFlipX()
    {
        return this.flipX;
    }

    public boolean getFlipY()
    {
        return this.flipY;
    }

    public void setSource(int sourceX, int sourceY)
    {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
    }

    public int getSourceX()
    {
        return this.sourceX;
    }

    public void setSourceX(int sourceX)
    {
        this.sourceX = sourceX;
    }

    public int getSourceY()
    {
        return this.sourceY;
    }

    public void setSourceY(int sourceY)
    {
        this.sourceY = sourceY;
    }

    public void setSourceSize(int sourceWidth, int sourceHeight)
    {
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
    }

    public int getSourceWidth()
    {
        return this.sourceWidth;
    }

    public void setSourceWidth(int sourceWidth)
    {
        this.sourceWidth = sourceWidth;
    }

    public int getSourceHeight()
    {
        return this.sourceHeight;
    }

    public void setSourceHeight(int sourceHeight)
    {
        this.sourceHeight = sourceHeight;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;

        setSize(texture.getWidth(), texture.getHeight());
        setSource(0, 0);
        setSourceSize(texture.getWidth(), texture.getHeight());
    }

    public Texture getTexture()
    {
        return this.texture;
    }
}

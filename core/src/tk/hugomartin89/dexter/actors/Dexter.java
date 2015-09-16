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
import com.badlogic.gdx.math.Intersector;
import tk.hugomartin89.dexter.Actor;
import tk.hugomartin89.dexter.Scene;
import tk.hugomartin89.dexter.DexterGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Dexter extends Actor
{
    private Texture standTexture;
    private Texture catchTexture;
    private Texture runningTexture;
    private Texture angryTexture;
    private State state;
    private float speed;

    public enum State
    {
        STAND,
        CATCH,
        MOVE_LEFT,
        MOVE_RIGHT,
        ANGRY
    }

    public Dexter()
    {
        AssetManager assetManager = DexterGame.getAssetManager();

        standTexture = assetManager.get("images/dexter_stand.png", Texture.class);
        catchTexture = assetManager.get("images/dexter_catch.png", Texture.class);
        runningTexture = assetManager.get("images/dexter_running.png", Texture.class);
        angryTexture = assetManager.get("images/dexter_angry.png", Texture.class);

        state = State.STAND;
        speed = 3.0F;

        setTexture(standTexture);
    }

    public void setState(State state)
    {
        updateState(state);

        this.state = state;
    }

    private void updateState(State state)
    {
        switch (state) {
            case ANGRY: {
                setTexture(angryTexture);

                setFlipX(false);
            }
            break;

            case CATCH: {
                setTexture(catchTexture);
            }
            break;

            case STAND: {
                setTexture(standTexture);

                setFlipX(!getFlipX());
            }
            break;

            case MOVE_LEFT: {
                setTexture(runningTexture);
                setFlipX(false);
            }
            break;

            case MOVE_RIGHT: {
                setTexture(runningTexture);
                setFlipX(true);
            }
            break;
        }
    }

    public State getState()
    {
        return state;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public void act(float delta)
    {
        Scene scene = (Scene)getStage();

        switch (state) {
            case MOVE_LEFT: {
                setX(getX() - speed);

                if (getX() < 0.0F) {
                    setX(0.0F);

                    setState(State.STAND);
                }
            }
            break;

            case MOVE_RIGHT: {
                setX(getX() + speed);

                if (getX() > scene.getWidth() - getWidth()) {
                    setX(scene.getWidth() - getWidth());

                    setState(State.STAND);
                }
            }
            break;

            default: {
            }
            break;
        }
    }

    public boolean collectPota(Pota pota)
    {
        Rectangle r1 = new Rectangle(getX(), getY(), standTexture.getWidth(), standTexture.getHeight());
        Rectangle r2 = new Rectangle(pota.getX(), pota.getY(), pota.getWidth(), pota.getHeight());

        if (Intersector.overlaps(r1, r2) && (r2.getY() >= 75.0F)) {
            setState(State.CATCH);

            setFlipX(pota.getX() <= getX() + getWidth() / 2.0F);

            return true;
        }

        return false;
    }

    public boolean collectBlock(Block block)
    {
        Rectangle r1 = new Rectangle(getX(), getY(), standTexture.getWidth(), standTexture.getHeight());
        Rectangle r2 = new Rectangle(block.getX(), block.getY(), block.getWidth(), block.getHeight());

        if (Intersector.overlaps(r1, r2) && (r2.getY() >= 75.0F)) {
            setState(State.ANGRY);

            return true;
        }

        return false;
    }
}

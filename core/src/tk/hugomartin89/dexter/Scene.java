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

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class Scene extends Stage
{
    public Scene()
    {
        super(new FitViewport(640.0F, 480.0F), Game.getSpriteBatch());
    }

    public abstract void create();

    public abstract void initialize();

    public abstract void leave(Scene paramScene);

    public abstract void resume(Scene paramScene);

    public void dispose()
    {
        super.dispose();
    }

} // class Scene

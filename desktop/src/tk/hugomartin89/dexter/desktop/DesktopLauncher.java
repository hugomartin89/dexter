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

package tk.hugomartin89.dexter.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tk.hugomartin89.dexter.DexterGame;

public class DesktopLauncher
{
  public static void main(String[] args)
  {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.resizable = false;
    config.width = 640;
    config.height = 480;
    config.useGL30 = false;
    config.addIcon("icon.png", Files.FileType.Internal);
    config.title = "El juego de Dexter";

    new LwjglApplication(new DexterGame(), config);
  }
}

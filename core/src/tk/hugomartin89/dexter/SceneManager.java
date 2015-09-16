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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;
import java.util.Map;

public class SceneManager implements Disposable
{
    private HashMap<String, Scene> scenesMap;
    private Scene activeScene;

    public SceneManager()
    {
        this.scenesMap = new HashMap<String, Scene>();
        this.activeScene = null;
    }

    public void addScene(String sceneId, Scene scene)
    {
        scene.create();
        scene.initialize();

        this.scenesMap.put(sceneId, scene);

        if (this.activeScene == null) {
            setActiveScene(sceneId);
        }
    }

    public Scene getActiveScene()
    {
        return this.activeScene;
    }

    public void setActiveScene(String sceneId)
    {
        Scene newScene = this.scenesMap.get(sceneId);

        if (this.activeScene != null) {
            this.activeScene.leave(newScene);
        }

        newScene.resume(this.activeScene);

        this.activeScene = newScene;
        this.activeScene.act();

        Gdx.input.setInputProcessor(this.activeScene);
    }

    public Scene getScene(String sceneId)
    {
        return scenesMap.get(sceneId);
    }

    public void dispose()
    {
        for (Map.Entry<String, Scene> entry : scenesMap.entrySet()) {
            entry.getValue().dispose();
        }
    }
}

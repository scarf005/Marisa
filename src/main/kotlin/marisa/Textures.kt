package marisa

import com.badlogic.gdx.graphics.Texture

private val textures = HashMap<String, Texture>()

/**
 * Loads the texture at [path] once and reuses it.
 *
 * Powers and relics are instantiated on every stack and copy, and nothing disposes their images,
 * so loading per instance leaks GPU memory.
 */
fun texture(path: String, load: (String) -> Texture = ::Texture): Texture =
    textures.getOrPut(path) { load(path) }

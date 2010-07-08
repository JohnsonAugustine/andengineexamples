package org.anddev.andengine.examples;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSCounter;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.graphics.Color;
import android.graphics.Typeface;

/**
 * @author Nicolas Gramlich
 * @since 20:06:15 - 08.07.2010
 */
public class ChangeableTextExample extends BaseExample {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Texture mFontTexture;
	private Font mFont;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera, false));
	}

	@Override
	public void onLoadResources() {
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR);

		this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLACK);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
	}

	@Override
	public Scene onLoadScene() {
		final FPSCounter fpsCounter = new FPSCounter();
		this.mEngine.registerPreFrameHandler(fpsCounter);

		final Scene scene = new Scene(1);
		scene.setBackgroundColor(0.09804f, 0.6274f, 0.8784f);

		final ChangeableText elapsedText = new ChangeableText(100, 160, this.mFont, "Seconds elapsed:", "Seconds elapsed: XXXXX".length());
		final ChangeableText fpsText = new ChangeableText(250, 240, this.mFont, "FPS:", "FPS: XXXXX".length());

		scene.getTopLayer().addEntity(elapsedText);
		scene.getTopLayer().addEntity(fpsText);
		
		scene.registerPreFrameHandler(new TimerHandler(0.05f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				elapsedText.setText("Seconds elapsed: " + ChangeableTextExample.this.mEngine.getSecondsElapsedTotal());
				fpsText.setText("FPS: " + fpsCounter.getFPS());
			}
		}));

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

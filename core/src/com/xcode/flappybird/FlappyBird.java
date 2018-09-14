package com.xcode.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture gameOver;
	ShapeRenderer shapeRenderer;
	ShapeRenderer shapeRenderer2;
	Texture[] birds;

	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	Circle birdCircle;
	Rectangle[] topTubeRectanbles;
	Rectangle[] bottomTubeRectangles;
	int flapState = 0;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	float birdY = 0;
	float velocity = 0;

	int gameState = 0;
	float gravity = 2;
	float maxTubeOffSet = 0;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffSet = new float[numberOfTubes];
	float distanceBetweenTubes;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		gameOver = new Texture("flappyGameOver.png");
		birdCircle = new Circle();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer2 = new ShapeRenderer();

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");


		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");

		maxTubeOffSet = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() / 2;
		topTubeRectanbles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		startRestartLogic();

	}

	@Override
	public void render () {

		batch.begin();
		drawBackgroud();

		switch (gameState) {
			case 1:
				gameStateOne();
				if (Gdx.input.justTouched()) {

					velocity = -30;

				}
				tubeLogic();

				if (birdY > 0) { //keep bird moving hax velocity < 0
					velocity = velocity + gravity;
					birdY -= velocity;
				} else {
					gameState = 2;
				}
				break;
			case 0:
				if (Gdx.input.justTouched()) {
					gameState = 1;
				}
				break;
			case 2:
				batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getWidth() / 2);

				if (Gdx.input.justTouched()) {
					gameState = 1;
					//call start restart logic
					startRestartLogic();
					score = 0;
					scoringTube = 0;
					velocity = 0;
				}
				break;

		}

//		if (gameState == 1) {
//
//			gameStateOne();
//
//			if (Gdx.input.justTouched()) {
//
//				velocity = -30;
//
//			}
//
//			tubeLogic();
//
//			if (birdY > 0) { //keep bird moving hax velocity < 0
//				velocity = velocity + gravity;
//				birdY -= velocity;
//			} else {
//				gameState = 2;
//			}

//		} else if (gameState == 0) {
//
//			if (Gdx.input.justTouched()) {
//				gameState = 1;
//			}
//			} else if (gameState == 2) {
//				batch.draw(gameOver, GDXWidth / 2 - gameOver.getWidth() / 2, GDXHeight / 2 - gameOver.getWidth() / 2);
//
//				if (Gdx.input.justTouched()) {
//					gameState = 1;
//					//call start restart logic
//					startRestartLogic();
//					score = 0;
//					scoringTube = 0;
//					velocity = 0;
//				}
//		}

		animateBird();

		font.draw(batch, String.valueOf(score), 100, 200);
		// birdCircle.set(Gdx.graphics.getWidth()/ 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

		collisionDetection();

		batch.end();
		shapeRenderer.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}

	public void drawBackgroud() {
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void gameStateOne() {
		if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

			score++;

			Gdx.app.log("Score", String.valueOf(score));

			if (scoringTube < numberOfTubes - 1) {
				scoringTube++;
			} else {
				scoringTube = 0;
			}
		}
	}

	public void tubeLogic() {
		for (int i = 0; i < numberOfTubes; i++) {

			if(tubeX[i] < - topTube.getWidth()) {
				tubeX[i] += numberOfTubes * distanceBetweenTubes;
				tubeOffSet[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			} else {
				tubeX[i] = tubeX[i] - tubeVelocity;

			}
			batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i]);
			batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i]);

			topTubeRectanbles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i], topTube.getWidth(), topTube.getHeight());
			bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i], bottomTube.getWidth(), bottomTube.getHeight());
		}
	}

	public void animateBird() {
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}
		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
	}

	public void startRestartLogic() {
		birdY = Gdx.graphics.getWidth()/ 2 - birds[0].getHeight() * 3/4;

		for (int i = 0; i < numberOfTubes; i++) {

			tubeOffSet[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			topTubeRectanbles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}
	}
	public void collisionDetection() {
		birdCircle.set(Gdx.graphics.getWidth()/ 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);
		/*
		ShapeRenderer logic for collision detection for the bird
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.CORAL);
		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		*/
		for (int i = 0; i < numberOfTubes; i++) {

			/*
			ShapRenderer logic for the top and bottom tubes

			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i], topTube.getWidth(), topTube.getHeight());
			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i], bottomTube.getWidth(), bottomTube.getHeight());
			*/
			if(Intersector.overlaps(birdCircle, topTubeRectanbles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
				Gdx.app.log("collison be happening", "yes");

				gameState = 2;
			}

		}
	}
}

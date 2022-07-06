package com.tufail.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import javax.management.StringValueExp;

import jdk.internal.net.http.common.Log;

public class myflappy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture gameover;
	//ShapeRenderer shapeRenderer;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;


	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	 float velocity = 0;
	Circle birdCircle;

	int gameState = 0;
	float gravity = 0.5F;

	Texture top_pipe;
	Texture bottom_pipe;
	float gap = 1300;
	float maxTubeOffset;
	Random Generator;
	int numberOftube = 4;
	float[] tubeOffset = new float[numberOftube];
	float tubeVelocity = 3;
	float[] tubex = new float[numberOftube];
	Rectangle[] topTubeRectangle;
	Rectangle[] bottomTubeRectangle;


	float distanceBetweenTube;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		// gameover = new Texture("game_over.jpg");

		//shapeRenderer= new ShapeRenderer();
		birdCircle = new Circle();

		birds = new Texture[6];
		birds[0] = new Texture("bird1.png");
		birds[1] = new Texture("bird1.png");
		birds[2] = new Texture("bird2.png");
		birds[3] = new Texture("bird2.png");
		birds[4] = new Texture("bird1.png");
		birds[5] = new Texture("bird1.png");

		top_pipe = new Texture("top_pipe.png");
		bottom_pipe = new Texture("bottom_pipe.png");

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		Generator = new Random();


		distanceBetweenTube = Gdx.graphics.getWidth() * 3 / 4;
		topTubeRectangle = new Rectangle[numberOftube];
		bottomTubeRectangle = new Rectangle[numberOftube];
		//startGame();
		for (int i = 0; i < numberOftube; i++) {
			tubeOffset[i] = (Generator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubex[i] = Gdx.graphics.getWidth() / 2 - top_pipe.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTube;
			topTubeRectangle[i] = new Rectangle();
			bottomTubeRectangle[i] = new Rectangle();

		}




	}


	@Override
	public void render() {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0) {
			if (tubex[scoringTube] < Gdx.graphics.getWidth() / 2) {
				score++;
				if (scoringTube < numberOftube - 1) {
					scoringTube++;

				} else {
					scoringTube = 0;
				}
			}


			if (Gdx.input.justTouched()) {

				velocity = -30;


			}
			for (int i = 0; i < numberOftube; i++) {
				if (tubex[i] < -top_pipe.getWidth()) {
					tubex[i] += numberOftube * distanceBetweenTube;
					tubeOffset[i] = (Generator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				} else {
					tubex[i] = tubex[i] - tubeVelocity;


				}
				//tubex[i] = tubex[i] - tubeVelocity;
				batch.draw(top_pipe, tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottom_pipe, tubex[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottom_pipe.getHeight() / 2 + tubeOffset[i]);
				topTubeRectangle[i] = new Rectangle(tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], top_pipe.getWidth(), top_pipe.getHeight());
				bottomTubeRectangle[i] = new Rectangle(tubex[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottom_pipe.getHeight() / 2 + tubeOffset[i], bottom_pipe.getWidth(), bottom_pipe.getHeight());
			}

			if (birdY > 0 || velocity < 0) {
				velocity = velocity + gravity;
				birdY -= velocity * 0.25;
			}
		} else {

			if (Gdx.input.justTouched()) {

				gameState = 1;

			}
		}
		if (flapState == 0) {
			flapState = 1;
		} else if (flapState == 1) {
			flapState = 2;
		} else if (flapState == 2) {
			flapState = 3;

		} else if (flapState == 3) {
			flapState = 4;
		} else if (flapState == 4) {
			flapState = 5;
		} else if (flapState == 5) {
			flapState = 0;

		}

		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		font.draw(batch, String.valueOf(score), 100, 300);
		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

		// shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);

		//shapeRenderer.circle(birdCircle.x,birdCircle.y, birdCircle.radius);
		for (int i = 0; i < numberOftube; i++) {
			//shapeRenderer.rect(tubex[i],Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset[i],top_pipe.getWidth(),top_pipe.getHeight());
			//shapeRenderer.rect(tubex[i] ,Gdx.graphics.getHeight()/2 - gap/2 - bottom_pipe.getHeight()/2 + tubeOffset[i],bottom_pipe.getWidth(),bottom_pipe.getHeight() );
			if (Intersector.overlaps(birdCircle, topTubeRectangle[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangle[i]));
			
			{
				//gameState = 2;
			}

			//shapeRenderer.end();
		}
			batch.end();


	}
}



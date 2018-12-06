package com.xcode.flappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class TitleScreen implements Screen {

    private Stage stage;
    private Game game;
    private Texture background;
    public String playerName = "";
    public TitleScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        background = new Texture(Gdx.files.internal("CrazyHelicopter.png"));
        Label title = new Label("Helicopter Wars", MyGdxGame.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);



//        Label nameLabel = new Label("Name:", MyGdxGame.gameSkin);
//        nameLabel.setAlignment(Align.center);
//        nameLabel.setY(Gdx.graphics.getHeight()*2/3);
//        nameLabel.setWidth(Gdx.graphics.getWidth());
//        stage.addActor(nameLabel);



//        TextField nameText = new TextField("sarthak", MyGdxGame.gameSkin);
//        Table table = new Table();
//        table.add(nameLabel);
//        table.add(nameText).width(100);
//        table.setY(Gdx.graphics.getHeight()*2/3);
//        table.setWidth(Gdx.graphics.getWidth());
//        playerName = nameText.getText();
//        stage.addActor(table);


        TextButton playButton = new TextButton("Play!",MyGdxGame.gameSkin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/2-playButton.getHeight()/2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new FlappyBird(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stage.addActor(playButton);

        TextButton optionsButton = new TextButton("Sensors",MyGdxGame.gameSkin);
        optionsButton.setWidth(Gdx.graphics.getWidth()/2);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2-optionsButton.getWidth()/2,Gdx.graphics.getHeight()/4-optionsButton.getHeight()/2);
        optionsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

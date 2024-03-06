package com.hvsa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainGame extends com.badlogic.gdx.Game {
	SpriteBatch batch;

  OrthographicCamera camera;
  ExtendViewport viewport;




  BitmapFont font;
  TextButton botaoIniciar;

  
  public long inicioTempo;
  public float TempoAtual, TempoAnt;
  Stage stage;

  public float telaWidth, telaHeight;

	@Override
	public void create () {
		batch = new SpriteBatch();
    

    //Camera
    camera = new OrthographicCamera();
    viewport = new ExtendViewport(1280, 720, camera);

    stage = new Stage(viewport, batch);

    //Iniciando o tamanho que sera da tela

    telaWidth = 1280;
    telaHeight = 720;
    Gdx.graphics.setWindowedMode((int)telaWidth, (int)telaHeight);




    //Botões do menu 
    font = new BitmapFont(Gdx.files.internal("Fonts/HeyComic.fnt"));
    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = font;

    botaoIniciar = new TextButton("Iniciar", buttonStyle);
    botaoIniciar.setPosition((telaWidth/2) - botaoIniciar.getWidth(), 400);

    botaoIniciar.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            MainGame.this.setScreen(new fase1(MainGame.this));
            System.out.printf("\nClicando");
        }
    });

    stage.addActor(botaoIniciar);


    Gdx.input.setInputProcessor(stage);

    MainGame.this.setScreen( new menu(MainGame.this));


  }


	@Override
	public void render () {
    super.render();
	}

  @Override
  public void resize(int width, int height) {

      viewport.update(width, height, true);

      batch.setProjectionMatrix(camera.combined);
  }

  private void informacao(){

   


  }
  private void update(){

            long currentTime = TimeUtils.nanoTime();
            TempoAtual = (currentTime - inicioTempo)/ 1_000_000_000.0f;

         

  }
	
	@Override
	public void dispose () {
		batch.dispose();
    stage.dispose();
    font.dispose();



	}
}

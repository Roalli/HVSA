package com.hvsa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class fase1 extends ScreenAdapter {

  MainGame game;
  Stage stage;

  OrthographicCamera camera;
  ExtendViewport viewport;
  Cenario ceu;
  Player jogador;
  Inimigo inimigos;
  BitmapFont bitmapfont;
  public long inicioTempo;
  public float TempoAtual, TempoAnt;
  private float distancia, distperc;


  private float telaWidth, telaHeight;


	public fase1(MainGame game) {



    this.game = game;
    stage = game.stage;

    //Inicio do contador de tempo
    inicioTempo = TimeUtils.nanoTime();

    //Iniciando as classes
    jogador = new Player();
    ceu = new Cenario();
    inimigos = new Inimigo();
    bitmapfont = new BitmapFont(Gdx.files.internal("Fonts/ChargeVector.fnt"));

    //Camera
    camera = new OrthographicCamera();
    viewport = new ExtendViewport(1280, 720, camera);

    //A distancia da fase

    distancia = 500;
    distperc = 0;
    
  }


  @Override
  public void show(){

    Gdx.input.setInputProcessor(stage);
  }

@Override
	public void render (float delta) {
    camera.update();
		ScreenUtils.clear(0.2f, 0.5f, 1, 1);

    this.update();

    if(jogador.getVida() > 0){


    if( jogador.getPV() > 0){//Confere se os pontos de vida é maior de 0 ou não

        this.testeColisao();




        game.batch.begin();
        //Chama os draw de cada classe 
        ceu.Draw(game.batch, jogador.GetVelocidade());
        inimigos.Draw(game.batch, jogador.GetVelocidade());
        jogador.Draw(game.batch, TempoAtual);
        this.informacao();//barra de informacao 
        game.batch.end();

    }else {//Ira resetar a fase para o inicio

        reset();
        jogador.resetPV();

    }


    }else {//Caso os pontos de PV for igual ou menor que 0
        game.batch.begin();
        Texture gameover = new Texture("GameOver.png");
        game.batch.draw( gameover, (viewport.getScreenWidth()/2) - (gameover.getWidth() / 2), (viewport.getScreenHeight()/2) - (gameover.getHeight() / 2));
        game.batch.end();
    }

	}

@Override
  public void resize(int width, int height) {

      viewport.update(width, height, true);

      game.batch.setProjectionMatrix(camera.combined);
  }

  private void informacao(){//Barra de informações contendo os pontos de vida
                            //a vida e a barra de distancia percorrida 

      bitmapfont.draw(game.batch,"PV = " + jogador.getPV(), 50 , Gdx.graphics.getHeight() - 5);
      bitmapfont.draw(game.batch, "Vida: " + jogador.getVida(), viewport.getScreenWidth() - 100, viewport.getScreenHeight() - 5);


  }

  private void testeColisao(){



      if(jogador.temBatata()){
      
          for(int cont = 0; cont < jogador.quantbatata(); cont++){
    
          
              jogador.batatacolidida(inimigos.Colissao(jogador.batataX(cont), jogador.batataY(cont), jogador.batataW( cont), jogador.batataH( cont ), true), cont);

          }
      }

      jogador.colissao(inimigos.Colissao(jogador.getX(), jogador.getY(), jogador.getWidth() - 20, jogador.getHeight(), true));

  }
  private void update(){

            long currentTime = TimeUtils.nanoTime();
            TempoAtual = (currentTime - inicioTempo)/ 1_000_000_000.0f;

            jogador.setTime(TempoAtual);

  }

  private void reset(){//Reseta a fase para o inicio 


      distperc = 0f;
      ceu.reset();
      inimigos.reset();


  }

  @Override
  public void pause(){
  }

  @Override
  public void resume(){
  }

  @Override 
  public void hide(){

  }

  
@Override
	public void dispose () {
		game.batch.dispose();
		jogador.dispose();
    ceu.dispose();
    bitmapfont.dispose();

	}
}

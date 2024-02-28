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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class menu extends ScreenAdapter {


  BitmapFont font;
  TextButton botaoIniciar, botaoOpcao, botaoSair;
  
  public long inicioTempo;
  public float TempoAtual, TempoAnt;
  Stage stage;

  private MainGame game;

    public menu (MainGame game) {

        this.game = game;

        stage = new Stage(game.viewport, game.batch);

    //Botões do menu 

    //Definindo a fonte 
    font = new BitmapFont(Gdx.files.internal("Fonts/HeyComic.fnt"));
    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = font;

    //Botão de iniciar 
    botaoIniciar = new TextButton("Iniciar", buttonStyle);
    botaoIniciar.setPosition((game.telaWidth/2) - (botaoIniciar.getWidth()/2), (game.telaHeight/2) - botaoIniciar.getHeight() - (game.telaHeight * (0.05f)));

    //Botao de opcoes
    botaoOpcao = new TextButton("Opções", buttonStyle);
    botaoOpcao.setPosition((game.telaWidth/2) - (botaoOpcao.getWidth()/2) , (game.telaHeight/2) - botaoOpcao.getHeight() - (game.telaHeight *(0.1f)) );

    //Botao de Sair
    botaoSair = new TextButton("Sair", buttonStyle);
    botaoSair.setPosition((game.telaWidth/2) - (botaoSair.getWidth()/2), (game.telaHeight/2) - botaoSair.getHeight() - (game.telaHeight * (0.15f)));


    //Instruções para o que seguir após de clicar nos textos 

    //Botao Iniciar 
    botaoIniciar.addListener(new ClickListener() {//Definição de quando clicar nele
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new fase1(game));
            System.out.printf("\nClicando");
        }
    });

    //Botao de Opções 
    botaoOpcao.addListener(new ClickListener(){//Definição de quando clicar nele 
        @Override
        public void clicked(InputEvent event, float x, float y) {
            //Instruções o que fazer
        }
    });

    //Botao para Sair
    botaoSair.addListener(new ClickListener(){//Definição de quando clicar nele
        @Override
        public void clicked(InputEvent event, float x, float y) {
           
            Gdx.app.exit();
        }
    });



    stage.addActor(botaoIniciar);
    stage.addActor(botaoOpcao);
    stage.addActor(botaoSair);

    Gdx.input.setInputProcessor(stage);


  }


	@Override
	public void render ( float delta) {//Parte que define oq ira renderizar
   
		ScreenUtils.clear(0.2f, 0.5f, 1, 1);
    game.camera.update();
 

    game.batch.begin();

    

    game.batch.end();

    stage.act();
    stage.draw();

	}

  @Override
  public void resize(int width, int height) {

      game.viewport.update(width, height, true);

      game.batch.setProjectionMatrix(game.camera.combined);
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

    stage.dispose();
    font.dispose();


	}


}

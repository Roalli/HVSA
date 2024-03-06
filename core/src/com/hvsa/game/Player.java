package com.hvsa.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;


public class Player {

    private float posY;
    private float posX;
    public float batataY;
    public float batataX;
    private float vel;
    private float tamanhoY;
    private float recargabatata;
    private boolean tiro;
    private boolean atirar;

    private Sprite moto;
    private Texture img;
    private Array <Sprite> batata;
    private float TempoAtual, TempoAnt;
    private long startTime;
    private int PV, vida, PVpadrao, pontuacao;
 

    //Parte para o touch

    private boolean touching;
    private float touchY, touchX;
    private char touchMode;

    public Player(){


        recargabatata = 0.5f;
        startTime = TimeUtils.nanoTime();
        TempoAtual = 0;
        TempoAnt = 0;
        posY = 0;
        posX = 0;
        vel = 1;
        tiro = false;
        atirar = false;
        PVpadrao = 3;
        PV = PVpadrao;
        vida = 5;


        pontuacao = 0;



        touching = false;
        touchX = 0;
        touchY = 0;
        touchMode = 'A';
        

        img = new Texture("modelo1.png");
        moto = new Sprite(img);
        tamanhoY = moto.getHeight();
        img = new Texture("batata.png");

        batata = new Array<Sprite>();
        


}

public void Draw(Batch batch, float tempo) {



    TempoAtual = tempo;
    this.DetectarTecla();
    this.bazuca(batch);
    batch.draw( moto, posX, posY);


}

private void DetectarTecla() {//Metodo para detectar as teclas predefinidas e seguir a função de
                              //cada tecla 


    //Parte para detecção de teclado

    //Detectando tecla para baixo
    if((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && posY > 0){

            posY-= 6;

        }

    //Detectando tecla para cima     
    if((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && posY < (Gdx.graphics.getHeight() - tamanhoY)){

            posY+= 6;
        }

    //Detectando tecla de espaço    
    if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){

            atirar = true;

        }

    //Detectar tecla para direito    
    if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && vel < 6f){

            if(TempoAtual - TempoAnt >= 1){
            
                TempoAnt = TempoAtual;
                vel++;
            }
        } else if( vel > 1){//Caso não estiver sendo acionada, reduz a velocidade

            if(TempoAtual - TempoAnt >= 0.2f){

                TempoAnt = TempoAtual;
                vel--;
            }
        }

    //Parte que detecta o touch


    //Ataque

    if(Gdx.input.isTouched()){


        if(Gdx.input.getX() > moto.getWidth()){

            atirar = true;

        }

    }


    //Movimento

    //Detecta no modo A

    if(Gdx.input.isTouched() && touchMode == 'A'){


    System.out.printf("\nPosicao moto: %f, %f\nPosicaoToque: %d, %d\nPosicaoDiferença: %f,%f\nPosicao total: 00, %d\n\n\n", posX, posY, Gdx.input.getX(), Gdx.input.getY(), touchX, touchY, (Gdx.graphics.getHeight() - Gdx.input.getY()));

        
        if(( ( Gdx.input.getX()) > posX && (Gdx.input.getX() ) < posX + moto.getWidth()) && (( Gdx.graphics.getHeight()  - 27  - Gdx.input.getY()) > posY && (Gdx.graphics.getHeight() -  Gdx.input.getY()) < posY + moto.getHeight() ) ){

            if(!touching){

            
                touchX = ( Gdx.graphics.getWidth() - Gdx.input.getX() ) - posX;
                touchY = ( Gdx.graphics.getHeight() - 27 - Gdx.input.getY() ) - posY;

            }

            touching = true;




        }

        if(touching){

            
            posY = ( Gdx.graphics.getHeight() - 27  - Gdx.input.getY() ) - touchY;

        }
        

    } else touching = false;

    //Se o modo do touch for B

    if(Gdx.input.isTouched() && touchMode == 'B'){

 
        if(( ( Gdx.input.getX()) > posX && (Gdx.input.getX() ) < posX + moto.getWidth()) ){


            if( (Gdx.graphics.getHeight() - 20 - Gdx.input.getY())  > ( (Gdx.graphics.getHeight() - 20)  / 2) && posY < (Gdx.graphics.getHeight() - tamanhoY) ){
                posY += 6;
            }


            if( (Gdx.graphics.getHeight() - 20 - Gdx.input.getY())  <= ( (Gdx.graphics.getHeight() - 20)  / 2) && posY > 0 ){
                posY -= 6;
            }



        }

    }


    }

    public void setTime( float time){//Insere o tempo atual na classe player
        TempoAtual = time;
    }

    private void bazuca(Batch batch){

        if(atirar){//Sendo verdadeiro, lança a batata da bazuca 


            atirar = false;
            if(TempoAtual - TempoAnt > recargabatata){

                
                Sprite batatas = new Sprite(img);
                batatas.setX(posX + 98);
                batatas.setY( posY + 100);
                batata.add( batatas );
                TempoAnt = TempoAtual;

            }

        }

            
        if(!batata.isEmpty()){//Se o array batata for vazio
            
            //Vai de cada array 
            for(Iterator<Sprite> iter = batata.iterator(); iter.hasNext();){ 
 
                Sprite batatas = iter.next();
                batch.draw(batatas, batatas.getX(), batatas.getY());
         
                if(batatas.getX() > Gdx.graphics.getWidth()){
                    iter.remove();
                } else{
                batatas.setX(batatas.getX() + 10); 
                }
                
            
            }    
        }    
        
    


        

    }

    public void batatacolidida( boolean acertou, int posicao){//Teste de colicao para batata 
        if(acertou){
            
            batata.removeIndex(posicao);

        }
    }

    // Metodos de retorno dos tamanhos dos sprites

    public float GetVelocidade(){
        return vel;
    }

    public float getX(){//Retorna a posicao do personagem em x
        return posX;
    }

    public float getY(){//Retorna a posicao do personagem em y
        return posY;
    }

    public float getWidth(){ // Retorna a largura do player 
        return moto.getWidth();
    }

    public float getHeight(){ // Retorna o tamanho em altura do player 
        return moto.getHeight();
    }

    public int quantbatata(){ // Retorna a quantidade de batata em movimento no momento
        return batata.size;
    }

    public boolean temBatata(){//Retorna se tem batata em movimento ou não
        return !batata.isEmpty();
    }

    public float batataH( int posicao){//retorna o tamanho da batata em altura, após dar o parametro de qual das batatas do array 
        
        Sprite batatas = batata.get(posicao);
        
        return batatas.getHeight();
    }

    public float batataW( int posicao ){//Retorna o tamanho da batata em largura, apos dar o parametro de qual das batatas do array 

        Sprite batatas = batata.get(posicao);

        return batatas.getWidth();
    }

    public float batataX( int posicao) {//Retorna a posicao em x da batata, so fornecer no parametro a posicao da batata no array 
        Sprite batatas = batata.get(posicao);

        return batatas.getX();
    }
  
    public float batataY( int posicao) {//Retorna a posicao em y da batata, so fornecer no parametro a posicao da batata o array 
        Sprite batatas = batata.get(posicao);

        return batatas.getY();
    }

    public void resetPV(){//Reseta os ponto de vida para o padrao 

        PV = PVpadrao;

    }
    public void setPVpadrao(int pv){//Define um novo padrao de pontos de vida 
 
        this.PVpadrao = pv;

    }

    public int getPV(){//Retorna quantos PV possui
        return PV;
    }

    public void setVida(int vida){//Altera a quantidade de vidas

        this.vida = vida;
    
    }

    public int getVida(){//Retorna a quantidade de vida 
        return vida;
    }


    public char getTouchMode(){//Retorna em que configuração de controle de touch está

        return touchMode;

    }

    public void setTouchMode( char mode ){//Altera o modo do touch 

        this.touchMode = mode;

    }


    public void setUpPontuacao(int pontos){//Aumenta a pontuacao

        this.pontuacao += pontos;

    }
  
    public int getPontuacao(){//Retorna a pontuacao do player
        return this.pontuacao;
    }



    // Fim dos metodos de retorno


    public void colissao(boolean colidiu){//Metodo para diminuir a vida do player ao colidir

        if(colidiu){
            PV--;
            if(PV == 0){
                vida--;
            }
        }

    }

    public void dispose(){

        img.dispose();
        batata.clear();


    }
}

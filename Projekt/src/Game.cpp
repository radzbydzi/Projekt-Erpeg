#include "Game.h"
#include<windows.h>
//wywalic to
std::vector<std::string> split(const std::string &text, char sep) {
  std::vector<std::string> tokens;
  std::size_t start = 0, end = 0;
  while ((end = text.find(sep, start)) != std::string::npos) {
    tokens.push_back(text.substr(start, end - start));
    start = end + 1;
  }
  tokens.push_back(text.substr(start));
  return tokens;
}
void Game::start()
{
    /*Tworzymy dwa w¹tki, jeden logiczny(ca³a mechanika gry),
    drugi obs³uguj¹cy wyœwietlanie ca³ej grafiki, zdarzenia
    klikniêcia myszki czy klawiatury
    */
    window.create(sf::VideoMode(800, 600), "My window");//przypisuje oknu właściwości, czyli jakby je tworzy,
                                                        //musi on być tu bo bogowie informatyki nie byli przychylni
    window.setActive(false);
    graphicalthread.launch(); // uruchomienie w¹tku
    logicalthread.launch();
    interactionThreadFunc();//interakcja z użytkownikiem w petli głownej

}
Game::~Game()
{
}
void Game::graphicsThreadFunc()
{
    cout<<"Jestem w¹tkiem graficznym"<<endl;
    sf::CircleShape shape(100.f);
    shape.setFillColor(sf::Color::Blue);
    cout<<"obiekt w klasie w grafice: "<<&this->currentMap.getObjectsList()[0]<<endl;
    while (window.isOpen())
    {
        window.clear();
        for(int i=0; i<currentMap.getObjectsList().size();i++)
        {
            Object* tmpObj;
            tmpObj = new Object(currentMap.getObjectsList()[i]);
            Animation* tmpAnimation=currentMap.getObjectsList()[i].getCurrentAnimation();
            cout<<"get y from "<<currentMap.getObjectsList()[0].getY() <<endl;
            sf::Sprite tmpSprite = tmpAnimation->getSprite();
            if(currentMap.getObjectsList()[i].getX() && currentMap.getObjectsList()[i].getY())
                tmpSprite.setPosition(currentMap.getObjectsList()[i].getX(), currentMap.getObjectsList()[i].getY());
            else
                tmpSprite.setPosition(0, 0);//jak nie ma x i y to w 0 0

            if(currentMap.getObjectsList()[i].getOriginX() && currentMap.getObjectsList()[i].getOriginY())
                tmpSprite.setOrigin(currentMap.getObjectsList()[i].getOriginX(), currentMap.getObjectsList()[i].getOriginY());

            if(currentMap.getObjectsList()[i].getRotation())
                tmpSprite.setRotation(currentMap.getObjectsList()[i].getRotation());

            window.draw(tmpSprite);//renderuje
            Sleep(500);
        }
        for(int i=0; i<currentMap.getPlayersList().size();i++)
        {
            Object tmpObj=currentMap.getPlayersList()[i];
            Animation* tmpAnimation=tmpObj.getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation->getSprite();
            if(tmpObj.getX()!=NULL && tmpObj.getY()!=NULL)
                tmpSprite.setPosition(tmpObj.getX(), tmpObj.getY());
            else
                tmpSprite.setPosition(0, 0);//jak nie ma x i y to w 0 0

            if(tmpObj.getOriginX()!=NULL && tmpObj.getOriginY()!=NULL)
                tmpSprite.setOrigin(tmpObj.getOriginX(), tmpObj.getOriginY());

            if(tmpObj.getRotation())
                tmpSprite.setRotation(tmpObj.getRotation());

            window.draw(tmpSprite);
        }
        /*for(int i=0; i<currentMap.getNPCList().size();i++)
        {
            Object tmpObj=currentMap.getNPCList()[i];
            Animation tmpAnimation=tmpObj.getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation.getSprite();
            if(tmpObj.getX() && tmpObj.getY())
                tmpSprite.setPosition(tmpObj.getX(), tmpObj.getY());
            else
                tmpSprite.setPosition(0, 0);//jak nie ma x i y to w 0 0

            if(tmpObj.getOriginX() && tmpObj.getOriginY())
                tmpSprite.setOrigin(tmpObj.getOriginX(), tmpObj.getOriginY());

            if(tmpObj.getRotation())
                tmpSprite.setRotation(tmpObj.getRotation());

            window.draw(tmpSprite);
        }*/

        window.display();//wyswietla caly obraz
    }
}

void Game::logicThreadFunc()
{
    cout<<"Jestem w¹tkiem logicznym"<<endl;
    //to jest tymczasowe
    Map* tmp=new Map("map1");
    Animation* tmpanim = new Animation("anim1",true);
    sf::Texture testowa;
    if(!testowa.loadFromFile("graphics/testcharactertileset.png"))
    {
        std::cout << "failed to load image" << std::endl;
        exit(1);
    }
            tmpanim->setTexture(&testowa);
            tmpanim->addFrame(0,0,32,64);
            tmpanim->addFrame(32,0,32,64);
            tmpanim->addFrame(64,0,32,64);
            tmpanim->addFrame(96,0,32,64);
            tmpanim->setCurrentFrame(0);
    tmpanim->setRepeat(true);

    Object* tmpobj = new Object("obj1");
    tmpobj->setY(20);

    tmpobj->addAnimation(tmpanim);

    tmp->addObject(*tmpobj);

    currentMap=*tmp;

    while(window.isOpen())
    {
        for(int i=0; i<taskQueue.size(); i++)
        {
            //parse taskQueue
            vector<string> splited = split(taskQueue[i]->command,':');
            if(splited[0]=="go")
            {
                //cout<<"rot: "<<tmpobj->getRotation()<<endl;

                if(splited[2]=="X")
                {
                    tmpobj->setX(tmpobj->getX()+atoi( splited[1].c_str() ));
                    cout<<"go: "<<tmpobj->getX()<<" : "<< splited[2]<<endl;
                }else if(splited[2]=="Y")
                {
                    currentMap.getObjectsList()[0].setY(currentMap.getObjectsList()[0].getY()+atoi( splited[1].c_str() ));
                    //cout<<"go: "<<tmpobj->getY()<<" : "<< splited[2]<<endl;
                    //cout<<"obiekt w tmp: "<<&tmp->getObjectsList()[0]<<endl;
                    //cout<<"obiekt w klasie: "<<&currentMap.getObjectsList()[0]<<endl;
                    cout<<"get y from "<<tmpobj->getY()<<" (tmp)" <<endl;
                    cout<<"get y from "<<currentMap.getObjectsList()[0].getY()<<" (kl)" <<endl;
                }else if(splited[2]=="XY")
                {
                    tmpobj->setX(tmpobj->getX()+atoi( splited[1].c_str() ));
                    tmpobj->setY(tmpobj->getY()+atoi( splited[1].c_str() ));
                    cout<<"go: "<<tmpobj->getX()<<" : "<< splited[2]<<endl;
                    cout<<"go: "<<tmpobj->getY()<<" : "<< splited[2]<<endl;
                }
            }
            taskQueue.pop_front();
        }
        tmpanim->setNextFrameAsCurrent();
//        cout<<"> "<<&tmpanim<<endl;
    }
}

void Game::interactionThreadFunc()
{
    cout<<"Jestem w¹tkiem interakcji"<<endl;
    //sf::RenderWindow window(sf::VideoMode(200, 200), "SFML works!");
    window.setActive(false);
    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
            else if(sf::Keyboard::isKeyPressed)
            {
                if(sf::Keyboard::isKeyPressed(sf::Keyboard::W))
                {
                    //mutex.lock();
                    taskQueue.push_back(new ThingsToDo(string("go:-20:Y"), getActiveObjectName(),1));
                    //mutex.unlock();
                }

            }
        }
    }
}
string Game::getActiveObjectName()
{
    return activeObjectName;
}

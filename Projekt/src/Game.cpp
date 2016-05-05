#include "Game.h"
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

    while (window.isOpen())
    {
        window.clear();
        for(int i=0; i<currentMap.getObjectsList().size();i++)
        {
            Object tmpObj=currentMap.getObjectsList()[i];
            Animation* tmpAnimation=tmpObj.getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation->getSprite();
            if(tmpObj.getX() && tmpObj.getY())
                tmpSprite.setPosition(tmpObj.getX(), tmpObj.getY());
            else
                tmpSprite.setPosition(0, 0);//jak nie ma x i y to w 0 0

            if(tmpObj.getOriginX() && tmpObj.getOriginY())
                tmpSprite.setOrigin(tmpObj.getOriginX(), tmpObj.getOriginY());

            if(tmpObj.getRotation())
                tmpSprite.setRotation(tmpObj.getRotation());

            window.draw(tmpSprite);//renderuje
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
    int ilosc = taskQueue.size();
    mutex.lock();
    Map tmp;
    //Cos sie kurwa zjebalo i nie mam bladego pojecia co; smutno bardzo; 01:09 6 maj 2016 over
    Animation* tmpanim;
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

    Object tmpobj;
    tmpobj.setName(string("obj1"));
    tmpobj.setXY(20,20);

    tmpobj.addAnimation(tmpanim);

    tmp.addObject(tmpobj);

    currentMap=tmp;

    mutex.unlock();
    while(window.isOpen())
    {
        mutex.lock();
        //tmpanim->setNextFrameAsCurrent();
//        cout<<"> "<<&tmpanim<<endl;
        mutex.unlock();
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
                    taskQueue.push_back(new ThingsToDo(string("go:20:Y"), getActiveObjectName(),1));
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

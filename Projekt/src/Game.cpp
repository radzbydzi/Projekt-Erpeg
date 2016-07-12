#include "Game.h"
#include<windows.h>
//wywalic to
bool Game::isRunning()
{
        return isRunningVar;
}
void Game::turnOff()
{
    isRunningVar=false;
}
void Game::loadMap(Map loadedMap)
{
    currentMap=loadedMap;
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
    for(int i = 0; i<taskQueue.size();i++)
        taskQueue.pop_back();
}
void Game::graphicsThreadFunc()
{
    cout<<"Jestem w¹tkiem graficznym"<<endl;
    while (window.isOpen())
    {
        window.clear();
        for(int i=0; i<currentMap.getObjectsList().size();i++)
        {
            Animation tmpAnimation=*currentMap.getObjectsList()[i]->getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation.getSprite();
            //cout<<currentMap.getObjectsList()[i]->getX()<<" "<<currentMap.getObjectsList()[i]->getY()<<endl;
            //cout<<currentMap.getObjectsList()[0]->getX()<<"|"<<currentMap.getObjectsList()[0]->getY()<<endl;
            tmpSprite.setPosition(currentMap.getObjectsList()[0]->getX(), currentMap.getObjectsList()[0]->getY());
            //cout<<tmpSprite.getPosition().x<<"|"<<tmpSprite.getPosition().y<<endl;
            //tmpSprite.setPosition(20,52);
            //if(currentMap.gehttp://allegro.pl/przewod-kabel-hdmi-hdmi-1-4a-plaski-10m-3d-i6061653496.htmltObjectsList()[i]->getOriginX() && currentMap.getObjectsList()[i]->getOriginY())
                //tmpSprite.setOrigin(currentMap.getObjectsList()[i]->getOriginX(), currentMap.getObjectsList()[i]->getOriginY());

            //if(currentMap.getObjectsList()[i]->getRotation())
                //tmpSprite.setRotation(currentMap.getObjectsList()[i]->getRotation());

            window.draw(tmpSprite);//renderuje
        }
        for(int i=0; i<currentMap.getPlayersList().size();i++)
        {
            Animation tmpAnimation=*currentMap.getPlayersList()[i]->getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation.getSprite();
            tmpSprite.setPosition(currentMap.getPlayersList()[i]->getX(), currentMap.getPlayersList()[i]->getY());
            //cout<<tmpSprite.getPosition().x<<" "<<tmpSprite.getPosition().y<<endl;
            window.draw(tmpSprite);//renderuje
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
    sf::Texture testowa;
    if(!testowa.loadFromFile("graphics/testcharactertileset.png"))
    {
        std::cout << "failed to load image" << std::endl;
        exit(1);
    }
    sf::Texture testowa2;
    if(!testowa2.loadFromFile("graphics/drzewo_burn2.png"))
    {
        std::cout << "failed to load image" << std::endl;
        exit(1);
    }
    sf::Texture kot;
    if(!kot.loadFromFile("graphics/koty.png"))
    {
        std::cout << "failed to load image" << std::endl;
        exit(1);
    }
    //walkup
    Animation walkUp;
    walkUp.setName("walkUp");
    walkUp.setKeepPlaying(false);
    walkUp.setTexture(&testowa);
    walkUp.addFrame(0,0,32,64);
    walkUp.addFrame(32,0,32,64);
    walkUp.addFrame(64,0,32,64);
    walkUp.addFrame(96,0,32,64);
    walkUp.setCurrentFrame(0);
    walkUp.setRepeat(true);
    //walkdown
    Animation walkDown = Animation("walkDown",true);
    walkDown.setTexture(&testowa);
    walkDown.addFrame(0,192,32,64);
    walkDown.addFrame(32,192,32,64);
    walkDown.addFrame(64,192,32,64);
    walkDown.addFrame(96,192,32,64);
    walkDown.setCurrentFrame(0);
    walkDown.setRepeat(true);
    //walkleft
    Animation walkLeft = Animation("walkLeft",true);
    walkLeft.setTexture(&testowa);
    walkLeft.addFrame(0,64,32,64);
    walkLeft.addFrame(32,64,32,64);
    walkLeft.addFrame(64,64,32,64);
    walkLeft.addFrame(96,64,32,64);
    walkLeft.setCurrentFrame(0);
    walkLeft.setRepeat(true);
    //walkup
    Animation walkRight = Animation("walkRight",true);
    walkRight.setTexture(&testowa);
    walkRight.addFrame(0,128,32,64);
    walkRight.addFrame(32,128,32,64);
    walkRight.addFrame(64,128,32,64);
    walkRight.addFrame(96,128,32,64);
    walkRight.setCurrentFrame(0);
    walkRight.setRepeat(true);
    //-----------
    //----
    Animation burn = Animation("treeBurn",true);
    burn.setTexture(&testowa2);
    burn.addFrame(0,0,148,148);
    burn.addFrame(148,0,148,148);
    burn.addFrame(296,0,148,148);
    burn.addFrame(444,0,148,148);
    burn.addFrame(592,0,148,148);
    burn.setCurrentFrame(0);
    burn.setRepeat(true);
    //
    //

    //
    Object* tree = new Object("tree");
    tree->addAnimation(burn);
    tree->setX(30);
    tree->setY(30);
    tree->addLongTermTask(ThingsToDo("animchg:treeBurn","d",1));
    tmp->addObject(tree);
    //cout<<tmpobj->getCurrentAnimation().getName()<<endl;
    Player* tmpobj = new Player("Avaline");
    cout<<"Avaline "<<tmpobj<<endl;

    tmpobj->addAnimation(walkUp);
    tmpobj->addAnimation(walkDown);
    tmpobj->addAnimation(walkLeft);
    tmpobj->addAnimation(walkRight);
    //------------------------------------
    //----
    //cout<<tmpobj->getCurrentAnimation().getName()<<endl;
    Player* tmpobj2 = new Player("George");
    cout<<"George "<<tmpobj2<<endl;

    tmpobj2->setX(200);
    tmpobj2->setY(200);

    tmpobj2->addAnimation(walkUp);
    tmpobj2->addAnimation(walkDown);
    tmpobj2->addAnimation(walkLeft);
    tmpobj2->addAnimation(walkRight);
    //------------------------------------
    //tmpobj->setX(100);
    //tmp->addObject(tmpobj);
    tmp->addPlayer(tmpobj);
    tmp->addPlayer(tmpobj2);
    currentMap=*tmp;
    setCurrentPlayer("George");
    while(window.isOpen())
    {
        mutex.lock();
        for(int i=0; i<currentMap.getObjectsList().size(); i++)
        {
            currentMap.getObjectsList()[i]->processNextTask();
        }
        //for(int i=0; i<currentMap.getNPCsList().size(); i++)
        //{
        //    currentMap.getNPCsList()[i]->processNextTask();
        //}
        for(int i=0; i<currentMap.getPlayersList().size(); i++)
        {
            currentMap.getPlayersList()[i]->processNextTask();
        }
        mutex.unlock();
//        cout<<"> "<<&tmpanim<<endl;
    }
}

void Game::interactionThreadFunc()
{
    cout<<"Jestem w¹tkiem interakcji"<<endl;
    //sf::RenderWindow window(sf::VideoMode(200, 200), "SFML works!");
    window.setActive(false);
    int xaq=0;
    int yaq=0;
    sf::Clock delay_clock;
        delay_clock.restart();
    while (window.isOpen())
    {
        sf::Event event;

        if(delay_clock.getElapsedTime().asMilliseconds()>10)
        {
                //currentMap.getPlayersList()[0]->addTask(new ThingsToDo(string("Dupa:1:23"), getActiveObjectName(),1));
                if(sf::Keyboard::isKeyPressed(sf::Keyboard::W))
                {
                    //cout<<"Adres player "<<getCurrentPlayer()<<endl;
                    getCurrentPlayer()->addTask(ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                    if(sf::Keyboard::isKeyPressed(sf::Keyboard::W) && sf::Keyboard::isKeyPressed(sf::Keyboard::D))
                    {
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:Y"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:1:X"), getActiveObjectName(),1));
                        //taskQueue.push_back(new ThingsToDo(string("animchg:walkLeft"), getActiveObjectName(),1));
                    }
                    else if(sf::Keyboard::isKeyPressed(sf::Keyboard::W) && sf::Keyboard::isKeyPressed(sf::Keyboard::A))
                    {
                        //taskQueue.push_back(new ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:Y"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:X"), getActiveObjectName(),1));
                    }
                    else
                    {
                        //getCurrentPlayer()->addTask(new ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:Y"), getActiveObjectName(),1));
                    }
                }

                else if(sf::Keyboard::isKeyPressed(sf::Keyboard::S))
                {
                    getCurrentPlayer()->addTask(ThingsToDo(string("animchg:walkUp"), getActiveObjectName(),1));
                    if(sf::Keyboard::isKeyPressed(sf::Keyboard::S) && sf::Keyboard::isKeyPressed(sf::Keyboard::D))
                    {
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:1:Y"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:1:X"), getActiveObjectName(),1));
                        //taskQueue.push_back(new ThingsToDo(string("animchg:walkLeft"), getActiveObjectName(),1));
                    }
                    else if(sf::Keyboard::isKeyPressed(sf::Keyboard::S) && sf::Keyboard::isKeyPressed(sf::Keyboard::A))
                    {
                        //taskQueue.push_back(new ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:1:Y"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:X"), getActiveObjectName(),1));
                    }
                    else
                    {
                        //getCurrentPlayer()->addTask(new ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                        getCurrentPlayer()->addTask(ThingsToDo(string("go:1:Y"), getActiveObjectName(),1));
                    }

                }else if(sf::Keyboard::isKeyPressed(sf::Keyboard::A))
                {
                    getCurrentPlayer()->addTask(ThingsToDo(string("animchg:walkLeft"), getActiveObjectName(),1));
                    getCurrentPlayer()->addTask(ThingsToDo(string("go:-1:X"), getActiveObjectName(),1));

                }else if(sf::Keyboard::isKeyPressed(sf::Keyboard::D))
                {
                    getCurrentPlayer()->addTask(ThingsToDo(string("animchg:walkRight"), getActiveObjectName(),1));
                    getCurrentPlayer()->addTask(ThingsToDo(string("go:1:X"), getActiveObjectName(),1));
                }
                if(sf::Keyboard::isKeyPressed(sf::Keyboard::C))
                {
                    cout<<"C"<<endl;
                    if(getCurrentPlayer()->getName()=="Avaline")
                    {
                        setCurrentPlayer("George");
                    }else
                    {
                        setCurrentPlayer("Avaline");
                    }
                }
                delay_clock.restart();
        }

        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }
    }
}
string Game::getActiveObjectName()
{
    return activeObjectName;
}
Player* Game::getCurrentPlayer()
{
    for(int i=0; i<currentMap.getPlayersList().size();i++)
    {
        if(currentMap.getPlayersList()[i]->getName()==currentPlayer)
        {
            return currentMap.getPlayersList()[i];
        }

    }
    return 0;
}
void Game::setCurrentPlayer(string name)
{
    currentPlayer=name;
}

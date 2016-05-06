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
            Animation* tmpAnimation=currentMap.getObjectsList()[i]->getCurrentAnimation();
            sf::Sprite tmpSprite = tmpAnimation->getSprite();
            //cout<<currentMap.getObjectsList()[i]->getX()<<" "<<currentMap.getObjectsList()[i]->getY()<<endl;
            //tmpSprite.setPosition(sf::Vector2f(currentMap.getObjectsList()[i]->getX(), currentMap.getObjectsList()[i]->getY()));
            //tmpSprite.setPosition(20,52);
            if(currentMap.getObjectsList()[i]->getOriginX() && currentMap.getObjectsList()[i]->getOriginY())
                tmpSprite.setOrigin(currentMap.getObjectsList()[i]->getOriginX(), currentMap.getObjectsList()[i]->getOriginY());

            if(currentMap.getObjectsList()[i]->getRotation())
                tmpSprite.setRotation(currentMap.getObjectsList()[i]->getRotation());

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
    //walkup
    Animation* walkUp = new Animation("walkUp",true);
    walkUp->setTexture(&testowa);
    walkUp->addFrame(0,0,32,64);
    walkUp->addFrame(32,0,32,64);
    walkUp->addFrame(64,0,32,64);
    walkUp->addFrame(96,0,32,64);
    walkUp->setCurrentFrame(0);
    walkUp->setRepeat(true);
    //walkdown
    Animation* walkDown = new Animation("walkDown",true);
    walkDown->setTexture(&testowa);
    walkDown->addFrame(0,192,32,64);
    walkDown->addFrame(32,192,32,64);
    walkDown->addFrame(64,192,32,64);
    walkDown->addFrame(96,192,32,64);
    walkDown->setCurrentFrame(0);
    walkDown->setRepeat(true);
    //walkleft
    Animation* walkLeft = new Animation("walkLeft",true);
    walkLeft->setTexture(&testowa);
    walkLeft->addFrame(0,64,32,64);
    walkLeft->addFrame(32,64,32,64);
    walkLeft->addFrame(64,64,32,64);
    walkLeft->addFrame(96,64,32,64);
    walkLeft->setCurrentFrame(0);
    walkLeft->setRepeat(true);
    //walkup
    Animation* walkRight = new Animation("walkRight",true);
    walkRight->setTexture(&testowa);
    walkRight->addFrame(0,0,32,64);
    walkRight->addFrame(32,0,32,64);
    walkRight->addFrame(64,0,32,64);
    walkRight->addFrame(96,0,32,64);
    walkRight->setCurrentFrame(0);
    walkRight->setRepeat(true);
    //-----------

    Object* tmpobj = new Object("obj1");
    //tmpobj->setX(100);
    //tmpobj->setY(100);

    tmpobj->addAnimation(walkUp);
    tmpobj->addAnimation(walkDown);
    tmpobj->addAnimation(walkLeft);
    tmpobj->addAnimation(walkRight);

    tmp->addObject(tmpobj);

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
                    //cout<<"go: "<<tmpobj->getX()<<" : "<< splited[2]<<endl;
                }else if(splited[2]=="Y")
                {
                    tmpobj->setY(tmpobj->getY()+atoi( splited[1].c_str() ));
                }
            }
            taskQueue.pop_front();
        }
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
                    taskQueue.push_back(new ThingsToDo(string("go:1:Y"), getActiveObjectName(),1));
                    taskQueue.push_back(new ThingsToDo(string("animchg:walkUp"), getActiveObjectName(),1));
                }if(sf::Keyboard::isKeyPressed(sf::Keyboard::S))
                {
                    taskQueue.push_back(new ThingsToDo(string("go:-1:Y"), getActiveObjectName(),1));
                    taskQueue.push_back(new ThingsToDo(string("animchg:walkDown"), getActiveObjectName(),1));
                }if(sf::Keyboard::isKeyPressed(sf::Keyboard::A))
                {
                    taskQueue.push_back(new ThingsToDo(string("go:1:X"), getActiveObjectName(),1));
                    taskQueue.push_back(new ThingsToDo(string("animchg:walkLeft"), getActiveObjectName(),1));
                }if(sf::Keyboard::isKeyPressed(sf::Keyboard::D))
                {
                    taskQueue.push_back(new ThingsToDo(string("go:-1:X"), getActiveObjectName(),1));
                    taskQueue.push_back(new ThingsToDo(string("animchg:walkRight"), getActiveObjectName(),1));
                }

            }
        }
    }
}
string Game::getActiveObjectName()
{
    return activeObjectName;
}

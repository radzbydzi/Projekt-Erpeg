#ifndef GAME_H
#define GAME_H

#include <SFML/Graphics.hpp>
#include <iostream>

using namespace std;

class Game
{
    public:
    Game()
        : graphicalthread(&Game::graphicsThreadFunc, this),//deklaracja konstruktora
        logicalthread(&Game::logicThreadFunc, this){};
    ~Game(); //deklaracja dekonstruktora
    void start();
    bool isRunning()
    {
        return isRunningVar;
    }
    void turnOff()
    {
        isRunningVar=false;
    }
private:
    sf::Thread graphicalthread;//deklaracja w¹tku z przypisaniem do adresu metody naszej klasy
    sf::Thread logicalthread;
    void graphicsThreadFunc();//deklaracja metody zawierajacej to co ma byæ wykonywane w w¹tku graficznym
    void logicThreadFunc();//deklaracja metody zawierajacej to co ma byæ wykonywane w w¹tku logicznym
    void interactionThreadFunc();
    sf::RenderWindow window;
    sf::Mutex mutex;
    bool isRunningVar = true;
};

#endif // GAME_H

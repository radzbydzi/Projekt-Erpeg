#ifndef GAME_H
#define GAME_H

#include <SFML/Graphics.hpp>
#include <iostream>
#include <deque>
#include <string>
#include "ThingsToDo.h"
#include "Map.h"
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
    void loadMap(Map loadedMap)
    {
        currentMap=loadedMap;
    }
    string getActiveObjectName();//metoda pobierajaca zmienna activeObjectName
private:
    //list<Map> lista_map;
    sf::Thread graphicalthread;//deklaracja w¹tku z przypisaniem do adresu metody naszej klasy
    sf::Thread logicalthread;
    void graphicsThreadFunc();//deklaracja metody zawierajacej to co ma byæ wykonywane w w¹tku graficznym
    void logicThreadFunc();//deklaracja metody zawierajacej to co ma byæ wykonywane w w¹tku logicznym
    void interactionThreadFunc();//funkcja wątka odpowiedzialnego za interakcje(klawiatura mysz) z uzytkonikiem; wywoływana w wątku głównym
    sf::RenderWindow window;//okno renderująca
    sf::Mutex mutex;// mutex zatrzymuje działanie wszystkich watkow by czekac na wykonanie ciągu instrukcji
                    //w którymś z wątków przez mutex.lock(); i mutex.unlock(); Istotny przy synchronizacji
                    //Nie można w jedynm czasie zapisywać danych z dwóch wątków do jdnego miejsca pamięci
    bool isRunningVar = true;
    deque<ThingsToDo*> taskQueue;
    bool mapLoaded=false;
    Map currentMap;//aktualnie wczytana mapa
    string activeObjectName;//przechowuje nazwe obiektu ktorym sterujemy
};

#endif // GAME_H

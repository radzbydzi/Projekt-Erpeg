#ifndef MAP_H
#define MAP_H
#include "Object.h"
#include "Player.h"
#include "NPC.h"
#include<deque>
using namespace std;
class Map
{
    public:
        Map();
        Map(string name);
        ~Map();
        //--------------------
        void addObject(Object* obj);
        void removeObject(int nr);
        void removeObject(string name);
        //===
        void addPlayer(Player* player);
        void removePlayer(int nr);
        void removePlayer(string name);
        //===
        void addNPC(NPC* npc);
        void removeNPC(int nr);
        void removeNPC(string name);
        //-------------------
        deque<Object*> getObjectsList();
        deque<Player*> getPlayersList();
        deque<NPC*> getNPCsList();
    protected:

    private:
        string name;
        deque<Object*> objects;//lista obiektow na mapie(czy tez sektorze)
        deque<Player*> players;//lista graczy na mapie(czy tez sektorze)
        deque<NPC*> npcs;//lista NPCtow na mapie(czy tez sektorze)
};

#endif // MAP_H

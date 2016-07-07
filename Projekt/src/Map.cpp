#include "Map.h"


Map::Map()
{
    //ctor
}
Map::Map(string name)
{
    this->name=name;
}
Map::~Map()
{
    for(int i = 0; i<objects.size();i++)
        objects.pop_back();
    for(int i = 0; i<players.size();i++)
        players.pop_back();
    for(int i = 0; i<npcs.size();i++)
        npcs.pop_back();
}
void Map::addObject(Object* obj)
{
    objects.push_back(obj);
}
void Map::removeObject(int nr)
{

}
void Map::removeObject(string name)
{

}
void Map::addPlayer(Player* player)
{
    players.push_back(player);
}
void Map::removePlayer(int nr)
{

}
void Map::removePlayer(string name)
{

}
void Map::addNPC(NPC* npc)
{
    npcs.push_back(npc);
}
void Map::removeNPC(int nr)
{

}
void Map::removeNPC(string name)
{

}
deque<Object*> Map::getObjectsList()
{
    return objects;
}
deque<Player*> Map::getPlayersList()
{
    return players;
}
deque<NPC*> Map::getNPCsList()
{
    return npcs;
}

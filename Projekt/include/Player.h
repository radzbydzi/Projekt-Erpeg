#ifndef PLAYER_H
#define PLAYER_H
#include "Object.h"
#include <string>
using namespace std;

class Player : public Object
{
    public:
        Player();
        ~Player();
        Player(string name);
    private:

};

#endif // PLAYER_H

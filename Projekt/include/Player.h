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
        //redefinicja z klasy Object
        void addAnimation(Animation anim);
        void removeAnimation();
        void setCurrentAnimation(int nr);
        void setCurrentAnimation(string name);
        Animation getCurrentAnimation();
        //------------------------
        void setName();
        void getName();
        //------------------------
        void setX(int x);
        void setY(int y);
        void setXY(int x, int y);
        int getX();
        int getY();
        //------------------------
        void setOriginX(int orx);
        void setOriginY(int ory);
        void setOriginXY(int orx, int ory);
        int getOriginX();
        int getOriginY();
        //------------------------
        void setRotation(int rot);//bezwzglednie
        int getRotation();//bezwzglednie
        void rotateObject();//wzglednie
        //redefinicja z klasy Object;
    private:
        //redefinicja z klasy Object
        int x;
        int y;
        int orx;
        int ory;
        int rot;
        string name;
        deque<Animation> animations;//lista dostepnych animiacji;
        int currentAnimation=0;//aktualnie wykonywana animacja
        //redefinicja z klasy Object;
};

#endif // PLAYER_H

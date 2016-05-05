#ifndef OBJECT_H
#define OBJECT_H
#include<string>
#include<deque>
#include"Animation.h"
using namespace std;

class Object
{
    public:
        Object();
        Object(string name);
        ~Object();
        void addAnimation(Animation* anim);
        void removeAnimation();
        void setCurrentAnimation(int nr);
        void setCurrentAnimation(string name);
        Animation* getCurrentAnimation();
        //------------------------
        void setName(string name);
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

    protected:

    private:
        int x;
        int y;
        int orx;
        int ory;
        int rot;
        string name;
        deque<Animation*> animations;//lista dostepnych animiacji;
        int currentAnimation=0;//aktualnie wykonywana animacja

};

#endif // OBJECT_H

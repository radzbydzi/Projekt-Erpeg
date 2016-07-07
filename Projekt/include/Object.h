#ifndef OBJECT_H
#define OBJECT_H
#include<string>
#include<deque>
#include"Animation.h"
#include "ThingsToDo.h"
using namespace std;

class Object
{
    public:
        Object();
        Object(string name);
        ~Object();
        void addAnimation(Animation anim);
        void removeAnimation();
        void setCurrentAnimation(int nr);
        void setCurrentAnimation(string name);
        Animation* getCurrentAnimation();
        //------------------------
        void setName(string name);
        string getName();
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
        void processNextTask();//przetwarza nastepne zadanie obiektu
        void addTask(ThingsToDo ttd);
        void delFrontTask();
        void delBackTask();
        void delAllTaskWithCommand(string command);
        //
        void addLongTermTask(ThingsToDo ttd);
        void delFrontLongTermTask();
        void delBackLongTermTask();
        void delLongTermTaskById(int id);
        void delAllLongTermTaskWithCommand(string command);

    protected:
        int x=0;
        int y=0;
        int orx=0;
        int ory=0;
        int rot=0;
        string name;
        deque<Animation> animations;//lista dostepnych animiacji;
        int currentAnimation=0;//aktualnie wykonywana animacja
        deque<ThingsToDo> taskQueue;//krotkoterminowe zadania
        deque<ThingsToDo> longTermTaskQueue;//dlugoterminowe zadania
    private:

};

#endif // OBJECT_H

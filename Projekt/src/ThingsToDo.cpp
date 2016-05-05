#include "ThingsToDo.h"

ThingsToDo::ThingsToDo(string command, string what, int importance)
{
    this->command=command;
    this->what=what;
    this->importance=importance;
}

ThingsToDo::~ThingsToDo()
{
    //dtor
}

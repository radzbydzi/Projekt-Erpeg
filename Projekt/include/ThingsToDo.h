#ifndef THINGSTODO_H
#define THINGSTODO_H
#include <string>
using namespace std;

class ThingsToDo
{
    public:
        ThingsToDo(string command, string what, int importance);
        ~ThingsToDo();
        string command;//komenda wg. wzoru: go:20px/X   go:-20px/Y go:20px/XY, explode, ignite etc. >>lista komend znajdzie sie w pliku txt
        string what; // co ma wykonac komende, nazwa obiektu lub gracza;
        int importance; // poziom waznosci naszej komendy; np. smierc ma wiekszy priorytet niz chodzenie
    protected:

    private:
};

#endif // THINGSTODO_H

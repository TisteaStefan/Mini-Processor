Librari folosite pentru citirea fisierului si eroare citirii:
import java.io.File
import java.io.FileNotFoundException

Variablie generale :
R-registru

PasJump-imi retine la ce linie trebuie sa fac jump-ul

PasJumpString-imi retine label ul

PasReal-retine linia curenta

OK-verificarea daca sunt intr-un jump ca sa ignor comenzile sau nu(1-nu folosesc jump 0-folosec jump si 2-pentru functia de "nop")

cmd- retine comanda pentru erorile de la jump

label-imi retine label-ul pentru erori

k-numarul de pasi pe care trebuie sa ii sar

i-indexul listei de stringuri citie din fisier

com-lista modificata fara spatii a fisierului


Pentru inceput realizez citirea din fisier si pentru fiecare linie sterg spatiile inutile cu "line.split(" ").filter { it != "" }" si le salvez in "com". Salvarea liniilor am facut-o separat la inceput pentru a putea face jump-urile inapoi.
Apoi parcurg "com"-ul unde mai intai verific daca am vreun jump de facut sau daca am avut comanda nop pentru comment-uri. Si apoi imi caut comanda de care am nevoie si ma redirectioneaza in functiile de comenzi de la final. Daca nu imi gaseste o comanda corecta imi apare eroarea de "unknown statement".

Functiile de comenzi:

-Functia Memory->(pt. push pop store si load)
                si pentru fiecare erorile respective

-Functia NoAction->(pt. nop)
                imi face OK-ul=3 pentru a stii ca sunt intr-un comentariu

-Functia Math->(pt. add,sub,mul,mod,div)
                -aici eroare o verific la inceput in caz ca stack-ul meu nu are destule elemente
                -dupa pentru fiecare caz operatia respectiva stergera din stack a elementelor folostie si adaugarea rezultatului in locul lor

-Functia Pseudo->(pt. stack,print)
                -la stack fac afisarea cu un for iar daca stack-ul este gol afisez doar "[ ]"
                -la print verific daca am elemente in stack pentru eroare si afisez elementul din stack

Functia Jump->(pt. jump,jumpz,jumpnz)

                -pentru jump verifica daca este jump de label sau jump de linie prin .isDigit() daca e un numar de int(Retin in PasJump) trebuie sa fac pentru linie daca este un String(Retin in PasJumpString) trebuie sa fac pentru label iar OK-ul de verificare daca am jump sau nu de la inceput devine 0

                -pentru jumpz si jumpnz procesul este acelasi doar cu veficarea stack-ului inainte ca comanda sa indeplineasca conditia de functionare daca nu OK devine 1 (nu folosec jump-ul) daca da OK devine 0 (folosesc jump-ul)

                -!! jump-urile sunt verificate la inceputul pargurgeri listei "com" ca sa stiu cand verific jump-ul am pus ca si conditii: ca PasJump sau PasJumpString sa fie diferit de 0 respectiv un string null dupa verific daca sunt la linia corecta comparant PasJump cu PasReal respectiv com[i] pentru label si daca conditiile sunt indeplinite imi continua cu restu de comenzi

                -!! jump-urile nu imi merg daca trebuie sa sar la o linie inapoi sau la un label inapoi doar inainte erorile pentru label gresit si linie out of bounds merg

Ex pentru ce retine "com"       fisier->("push 2")

i=1->(push)

i=2->(2)

Pentru asta la fiecare comanda care are mai mult de un 'parametru' cresc index-ul mereu pentru a nu verifica un numar random si a avea eroarea de unknown command. De aici si problema cu intoarcerea cu jump-ul. Si doar acolo am avut probleme in testare si variabila "k" care imi retine de cate ori am sarit un pas pentru a putea sa ma intorc.

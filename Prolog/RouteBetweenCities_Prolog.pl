%knowledge base
flight(istanbul,izmir,3).
flight(istanbul,ankara,5).
flight(istanbul,trabzon,3).
flight(edirne,edremit,5).
flight(edremit,erzincan,7).
flight(izmir,antalya,1).
flight(izmir,ankara,6).
flight(ankara,trabzon,2).
flight(ankara,konya,8).
flight(antalya,diyarbakir,5).
flight(konya,kars,5).
flight(konya,diyarbakir,1).
flight(kars,gaziantep,3).

%rules
subroute(X,Y,C) :- flight(X,Y,C);flight(Y,X,C).
minimo([X], X) :-!.
minimo([X,Y|Tail], N):-( X > Y -> minimo([Y|Tail], N);
        minimo([X|Tail], N)).

myRoute(FromCity, ToCity, [FromCity,ToCity],[C],C) :-
  subroute(FromCity,ToCity,C).

myRoute(FromCity, ToCity,[FromCity|Connections],[F|A],F) :- 
  subroute(FromCity,ToConnection,C),
  myRoute(ToConnection, ToCity, Connections,A,E),F is C+E,\+member(ToCity,Connections),write(ToCity).

route(X,Z,E) :- myRoute(X,Z,_D,_F,E).

croute(X,Z,E) :-myRoute(X,Z,_D,F,_S),!,minimo(F,V),E is V.
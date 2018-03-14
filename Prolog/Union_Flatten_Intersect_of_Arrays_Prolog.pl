%
union([H|T],[],[H|T]).
union([],[],[]).     
union([],[H|T],[H|T]).   
union([H|T], LIST2, LIST) :- member(H,LIST2), union(T,LIST2,LIST),!.    
union([H|T], LIST2, [H|LIST]) :- \+(member(H,LIST2)), union(T,LIST2,LIST).

%intersection of two lists
inter(_, [], []).
inter([], _, []).
inter([HEAD1|TAIL1], LIST2, [HEAD1|LIST]):- member(HEAD1, LIST2),
    inter(TAIL1, LIST2, LIST),!.
inter([_|TAIL1], LIST2, LIST):-
    inter(TAIL1, LIST2, LIST).

intersect(L, S,U):-
 inter(L, S, U).


%a prejucate that flatten a list of list of list up to any level of deepness. 
flatten([], []).
flatten([HEAD|TAIL], FlatLIST) :-!,flatten(HEAD, NewLIST),
    							 flatten(TAIL, NewLISTTAIL),
    							 append(NewLIST, NewLISTTAIL, FlatLIST).

flatten(HEAD, [HEAD]).
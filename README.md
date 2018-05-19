"# Video-Player"

features

-using exoplayer Api to stream video
- handle change configuration
- start from any position

-I use MVVM and android architcure component for design pattern , databinding , dagger2,

-I use Junit and Robolectric for test

project structure

-Network Module :- this is module I have created to handle all kind of http requests
I added layer up lib Fast-Android-Networking then I have created NetworkBoundResource to handle local and server connection

- app module:- this is video player module
as I'm using MVVM so I have main packages are

1- data :- for data layer local or remote contain VideoPlayerDataModel

3-models :- models that I'm using for parser

3-views
- VideoPlayerActivity :-player activity
-playerAdapter:- adapter to create player recycler view

4-ModelView:- VideoPlayerActivityViewModel to handle business for view
videoPlayerModule :- this is class to provide instance as I'm using dagger

others packages

di :- utils class for dagger
callback:- it has callback class to communicate between layout and controller


test

I have test this app on s7 , s8 and emulator for tablet.

I did some test case using Robolectric for VideoPlayerActivity to check configuration for different mobile and tablet.


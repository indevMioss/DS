package com.interdev.dstrike.screens;

import com.badlogic.gdx.Screen;

public enum Screens {
    GAME_MENU {
        @Override
        protected Screen getScreenInstance() {
            return new GameMenuScreen();
        }
    },

    GAME {
        @Override
        protected Screen getScreenInstance() {
            return new GameScreen();
        }
    },

    INSTRUCTIONS {
        @Override
        protected Screen getScreenInstance() {
            return new InstructionsScreen();
        }
    },

    INVITE_FRIEND {
        @Override
        protected Screen getScreenInstance() {
            return new InviteFriendScreen();
        }
    },

    LEADERBOARD {
        @Override
        protected Screen getScreenInstance() {
            return new LeaderboardScreen();
        }
    },

    LOADING {
        @Override
        protected Screen getScreenInstance() {
            return new LoadingScreen();
        }
    },

    OPTIONS {
        @Override
        protected Screen getScreenInstance() {
            return new OptionsScreen();
        }
    },

    START_MENU {
        @Override
        protected Screen getScreenInstance() {
            return new StartMenuScreen();
        }
    };


    protected abstract Screen getScreenInstance();
}

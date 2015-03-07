package com.interdev.dstrike.screens;

import com.badlogic.gdx.Screen;
import com.interdev.dstrike.screens.Utils.AuthorizationScreen;
import com.interdev.dstrike.screens.game.GameScreen;

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
    },

    AUTHORIZATION {
        @Override
        protected Screen getScreenInstance() {
            return new AuthorizationScreen();
        }
    };


    protected abstract Screen getScreenInstance();
}

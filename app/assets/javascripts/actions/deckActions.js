import axios from 'axios';

import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

const DeckActions = {
  loadDecksForUser() {
    // NOTE: This is a good place for API calls to live.
    axios.get('/decks/?finder=byUserId&userId=1').then((response) => {
      Dispatcher.dispatch({
        actionType: ActionTypes.LOADED_DECKS_FOR_USER,
        decks: response.data,
      });
    });
  },

  loadDeck(deckId) {
    axios.get(`/cards/?finder=byDeckId&deckId=${deckId}`).then((response) => {
      Dispatcher.dispatch({
        actionType: ActionTypes.LOADED_DECK,
        cards: response.data,
        deckId,
      });
    });
  },
};

export default DeckActions;

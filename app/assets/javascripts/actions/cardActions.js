import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

class CardActions {

  addNewItem(card) {
    // NOTE: This is a good place for API calls to live.
    Dispatcher.dispatch({
      actionType: ActionTypes.CREATE_CARD,
      payload: card
    });
  }

}

export default new CardActions();

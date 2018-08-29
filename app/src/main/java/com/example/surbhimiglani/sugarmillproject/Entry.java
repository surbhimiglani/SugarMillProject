package com.example.surbhimiglani.sugarmillproject;

import java.util.List;

/**
 * Created by Surbhi Miglani on 03-03-2018.
 */

public class Entry {

    /**
     * result : 0
     * messages : {"messages":[{"ID":1,"EntryNumber":"3"},{"ID":2,"EntryNumber":"3"},{"ID":3,"EntryNumber":"1"},{"ID":4,"EntryNumber":"2"},{"ID":5,"EntryNumber":"7778"},{"ID":6,"EntryNumber":"19999"},{"ID":7,"EntryNumber":"3"},{"ID":8,"EntryNumber":"74787578"},{"ID":9,"EntryNumber":"666677"},{"ID":10,"EntryNumber":"998888"}]}
     */

    private String result;
    private MessagesBeanX messages;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MessagesBeanX getMessages() {
        return messages;
    }

    public void setMessages(MessagesBeanX messages) {
        this.messages = messages;
    }

    public static class MessagesBeanX {
        private List<MessagesBean> messages;

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public static class MessagesBean {
            /**
             * ID : 1
             * EntryNumber : 3
             */

            private int ID;
            private String EntryNumber;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getEntryNumber() {
                return EntryNumber;
            }

            public void setEntryNumber(String EntryNumber) {
                this.EntryNumber = EntryNumber;
            }
        }
    }
}

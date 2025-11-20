package com.tencent.imsdk.message;

import com.tencent.imsdk.group.GroupMemberInfo;
import com.tencent.imsdk.relationship.UserInfo;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    public static int MESSAGE_TYPE_UNKNOWN = 0x0;
    public static int MESSAGE_TYPE_C2C = 0x1;
    public static int MESSAGE_TYPE_GROUP = 0x2;
    public static int MESSAGE_TYPE_MULTI_SYNC = 0x3;

    public static int PLATFORM_OTHER = 0;
    public static int PLATFORM_WINDOWS = 1;
    public static int PLATFORM_ANDROID = 2;
    public static int PLATFORM_IOS = 3;
    public static int PLATFORM_MAC = 4;
    public static int PLATFORM_SIMULATOR = 5;

    public static final int V2TIM_MSG_STATUS_SENDING = 1;
    public static final int V2TIM_MSG_STATUS_SUCCESS = 2;
    public static final int V2TIM_MSG_STATUS_SEND_FAILED = 3;
    public static final int V2TIM_MSG_STATUS_DELETED = 4;
    public static final int V2TIM_MSG_STATUS_LOCAL_IMPORTED = 5;
    public static final int V2TIM_MSG_STATUS_REVOKED = 6;

    private String msgID = "";
    private int messageType;
    private long clientTime;
    private long serverTime;
    private String senderUserID;
    private long senderTinyID;
    private String receiverUserID;
    private long receiverTinyID;
    private String nickName;
    private String friendRemark;
    private String faceUrl;
    private String nameCard;
    private String groupID;
    private boolean isForward;
    private boolean isMessageSender = true;
    private boolean isSelfRead;
    private boolean isPeerRead;
    private boolean receiptPeerRead;
    private long receiptTime;
    private long random;
    private long seq;
    private int lifeTime = -1;
    private int messageStatus;
    private int priority;
    private MessageOfflinePushInfo offlinePushInfo;
    private int localCustomNumber;
    private String localCustomString;
    private byte[] cloudCustomBytes;
    private List<MessageBaseElement> messageBaseElements = new ArrayList<>();
    private int platform;
    private List<MessageAtInfo> messageGroupAtInfoList = new ArrayList<>();
    private boolean excludedFromUnreadCount = false; // true - 不计入未读数，false - 计入未读数
    private boolean excludedFromLastMessage = false; // true - 不更新会话最后一条消息，false - 更新会话最后一条消息
    private boolean excludedFromContentModeration = false; // true - 不过内容审核，false - 需要过内容审核
    private String customModerationConfigurationID; // 消息审核自定义配置 ID
    private boolean disableCloudMessagePreHook =
        false; // true - 禁用消息发送前云端回调，false - 不禁用消息发送前云端回调
    private boolean disableCloudMessagePostHook =
        false; // true - 禁用消息发送后云端回调，false - 不禁用消息发送后云端回调
    private List<String> targetGroupMemberList = new ArrayList<>(); // 群消息可见的群成员列表
    private UserInfo revokerInfo;
    private String revokeReason;
    private GroupMemberInfo pinnerInfo;

    private boolean needReadReceipt = false;
    private boolean hasSentReceipt = false;
    private int receiptReadCount = 0;
    private int receiptUnreadCount = -1;

    private boolean supportMessageExtension = false;

    private long messageVersion;

    private boolean isBroadcastMessage = false;

    private boolean hasRiskContent = false;
    private int riskTypeIdentified = 0;

    public String getMsgID() {
        return msgID;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getTimestamp() {
        if (serverTime > 0) {
            return serverTime;
        }
        return clientTime;
    }

    public long getClientTime() {
        return clientTime;
    }

    public void setClientTime(long clientTime) {
        this.clientTime = clientTime;
    }

    public String getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getReceiverUserID() {
        return receiverUserID;
    }

    public void setReceiverUserID(String receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public String getNameCard() {
        return nameCard;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setForward(boolean forward) {
        isForward = forward;
    }

    public boolean isMessageSender() {
        return isMessageSender;
    }

    public void setIsMessageSender(boolean isMessageSender) {
        this.isMessageSender = isMessageSender;
    }

    public boolean isSelfRead() {
        if (isSelfRead) {
            return true;
        } else {
            isSelfRead = MessageCenter.getInstance().isMessageSelfRead(getMessageKey());
            return isSelfRead;
        }
    }

    public boolean isPeerRead() {
        if (isPeerRead) {
            return true;
        } else {
            isPeerRead = MessageCenter.getInstance().isMessagePeerRead(getMessageKey());
            return isPeerRead;
        }
    }

    public boolean isReceiptPeerRead() {
        return receiptPeerRead;
    }

    public long getReceiptTime() {
        return receiptTime;
    }

    public long getRandom() {
        return random;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int status) {
        messageStatus = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MessageOfflinePushInfo getOfflinePushInfo() {
        return offlinePushInfo;
    }

    public void setOfflinePushInfo(MessageOfflinePushInfo offlinePushInfo) {
        this.offlinePushInfo = offlinePushInfo;
    }

    public int getLocalCustomNumber() {
        return localCustomNumber;
    }

    public void setLocalCustomNumber(int customNumberInfo) {
        this.localCustomNumber = customNumberInfo;
        MessageCenter.getInstance().setLocalCustomNumber(this, customNumberInfo);
    }

    public String getLocalCustomString() {
        return localCustomString;
    }

    public void setLocalCustomString(String customStringInfo) {
        this.localCustomString = customStringInfo;
        MessageCenter.getInstance().setLocalCustomString(this, customStringInfo);
    }

    public String getCloudCustomString() {
        String cloudCustomString = "";
        if (cloudCustomBytes != null && cloudCustomBytes.length > 0) {
            try {
                cloudCustomString = new String(cloudCustomBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return cloudCustomString;
    }

    public void setCloudCustomString(String cloudCustomData) {
        if (cloudCustomData == null) {
            cloudCustomData = "";
        }
        try {
            this.cloudCustomBytes = cloudCustomData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addElement(MessageBaseElement messageBaseElement) {
        if (messageBaseElement == null) {
            return;
        }
        messageBaseElements.add(messageBaseElement);
    }

    public List<MessageBaseElement> getMessageBaseElements() {
        return messageBaseElements;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setMessageBaseElements(List<MessageBaseElement> messageBaseElements) {
        this.messageBaseElements = messageBaseElements;
    }

    protected void addMessageGroupAtInfo(MessageAtInfo messageAtInfo) {
        this.messageGroupAtInfoList.add(messageAtInfo);
    }

    public void setMessageGroupAtInfoList(List<MessageAtInfo> messageGroupAtInfoList) {
        this.messageGroupAtInfoList = messageGroupAtInfoList;
    }

    public List<MessageAtInfo> getMessageGroupAtInfoList() {
        return messageGroupAtInfoList;
    }

    public boolean isExcludedFromUnreadCount() {
        return excludedFromUnreadCount;
    }

    public void setExcludedFromUnreadCount(boolean excludedFromUnreadCount) {
        this.excludedFromUnreadCount = excludedFromUnreadCount;
    }

    public boolean isExcludedFromLastMessage() {
        return excludedFromLastMessage;
    }

    public void setExcludedFromLastMessage(boolean excludedFromLastMessage) {
        this.excludedFromLastMessage = excludedFromLastMessage;
    }

    public boolean isExcludedFromContentModeration() {
        return excludedFromContentModeration;
    }

    public void setExcludedFromContentModeration(boolean excludedFromContentModeration) {
        this.excludedFromContentModeration = excludedFromContentModeration;
    }

    public String getCustomModerationConfigurationID() {
        return this.customModerationConfigurationID;
    }

    public void setCustomModerationConfigurationID(String customModerationConfigurationID) {
        this.customModerationConfigurationID = customModerationConfigurationID;
    }

    public void setTargetGroupMemberList(List<String> targetGroupMemberList) {
        this.targetGroupMemberList = targetGroupMemberList;
    }

    public List<String> getTargetGroupMemberList() {
        return targetGroupMemberList;
    }

    public boolean isNeedReadReceipt() {
        return needReadReceipt;
    }

    public void setNeedReadReceipt(boolean needReadReceipt) {
        this.needReadReceipt = needReadReceipt;
    }

    public boolean isHasSentReceipt() {
        return hasSentReceipt;
    }

    public void setHasSentReceipt(boolean hasSentReceipt) {
        this.hasSentReceipt = hasSentReceipt;
    }

    public int getReceiptReadCount() {
        return receiptReadCount;
    }

    public void setReceiptReadCount(int receiptReadCount) {
        this.receiptReadCount = receiptReadCount;
    }

    public int getReceiptUnreadCount() {
        return receiptUnreadCount;
    }

    public void setReceiptUnreadCount(int receiptUnreadCount) {
        this.receiptUnreadCount = receiptUnreadCount;
    }

    public boolean isBroadcastMessage() {
        return isBroadcastMessage;
    }

    public boolean isSupportMessageExtension() {
        return supportMessageExtension;
    }

    public void setSupportMessageExtension(boolean supportMessageExtension) {
        this.supportMessageExtension = supportMessageExtension;
    }

    public void setHasRiskContent(boolean hasRiskContent) {
        this.hasRiskContent = hasRiskContent;
    }

    public boolean isHasRiskContent() {
        return this.hasRiskContent;
    }

    public boolean isDisableCloudMessagePreHook() {
        return this.disableCloudMessagePreHook;
    }

    public void setDisableCloudMessagePreHook(boolean disableCloudMessagePreHook) {
        this.disableCloudMessagePreHook = disableCloudMessagePreHook;
    }

    public boolean isDisableCloudMessagePostHook() {
        return this.disableCloudMessagePostHook;
    }

    public void setDisableCloudMessagePostHook(boolean disableCloudMessagePostHook) {
        this.disableCloudMessagePostHook = disableCloudMessagePostHook;
    }

    public UserInfo getRevokerInfo() {
        return revokerInfo;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public GroupMemberInfo getPinnerInfo() {
        return pinnerInfo;
    }

    public MessageKey getMessageKey() {
        MessageKey messageKey = new MessageKey();
        messageKey.setMessageID(msgID);
        messageKey.setMessageType(messageType);
        messageKey.setIsMessageSender(isMessageSender);
        messageKey.setSenderUserID(senderUserID);
        messageKey.setSenderTinyID(senderTinyID);
        messageKey.setReceiverUserID(receiverUserID);
        messageKey.setReceiverTinyID(receiverTinyID);
        messageKey.setGroupID(groupID);
        messageKey.setClientTime(clientTime);
        messageKey.setServerTime(serverTime);
        messageKey.setSeq(seq);
        messageKey.setRandom(random);

        return messageKey;
    }

    public void update(Message message) {
        msgID = message.msgID;
        messageType = message.messageType;
        isMessageSender = message.isMessageSender;
        senderUserID = message.senderUserID;
        senderTinyID = message.senderTinyID;
        receiverUserID = message.receiverUserID;
        receiverTinyID = message.receiverTinyID;
        groupID = message.groupID;
        clientTime = message.clientTime;
        serverTime = message.serverTime;
        seq = message.seq;
        random = message.random;
        messageStatus = message.messageStatus;
        riskTypeIdentified = message.riskTypeIdentified;

        for (MessageBaseElement element : messageBaseElements) {
            for (MessageBaseElement newElem : message.messageBaseElements) {
                if (element.update(newElem)) {
                    break;
                }
            }
        }
    }
}

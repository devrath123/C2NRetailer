package com.example.c2n.core;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.c2n.R;
import com.example.c2n.core.models.TokenDataModel;
import com.example.c2n.initial_phase.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by shivani.singh on 23-04-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToDatabase(token);
    }

    /*@Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "M_CH_ID")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("ProductFinder")
                        .setContentText(remoteMessage.getNotification().getBody());

        Intent notificationIntent = null;
//        if (((String) remoteMessage.getData().get("userType")).equals("customer")) {
        notificationIntent = new Intent(this, CustomerHomeActivity.class);
//        } else if (((String) remoteMessage.getData().get("userType")).equals("retailer")) {
//            notificationIntent = new Intent(this, RetailerHomeActivity.class);
//        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//            notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        String userType = remoteMessage.getData().get("userType");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("userType", userType);
//        if (userType.equals("retailer")) {
//            notificationIntent = new Intent(this, RetailerHomeActivity.class);
//        } else if (userType.equals("customer")) {
//            notificationIntent = new Intent(this, CustomerHomeActivity.class);
//    }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
//                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("ProductFinder")
                .setContentText(remoteMessage.getNotification().getBody());
//                .setContentInfo("Info");


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        notificationBuilder.setAutoCancel(true);

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    private void sendRegistrationToDatabase(String token) {
        SharedPrefManager.Init(this);
        SharedPrefManager.LoadFromPref();
        if (!SharedPrefManager.get_userDocumentID().equals("")) {
            if (SharedPrefManager.get_userType().equals("R")) {
                database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.get("retailer") != null) {
                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .update("retailer", token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                } else if (documentSnapshot.get("customer") != null) {
                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .update("retailer", token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                } else {
                                    TokenDataModel tokenDataModel = new TokenDataModel();
                                    tokenDataModel.setRetailer(token);

                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .set(tokenDataModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                }
//                                if (documentSnapshot.get("retailer") != null) {
////                                    HashMap<String, String> stringTokenDataModelHashMap = (HashMap<String, String>) documentSnapshot.get("token");
////                                    stringTokenDataModelHashMap.put("retailer", token);
//                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
//                                            .update("retailer", token)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "onSuccess: ");
//                                                }
//                                            });
//                                } else if (documentSnapshot.get("customer") != null && documentSnapshot.get("retailer") != null) {
//
//                                } else {
//                                    TokenDataModel tokenDataModel = new TokenDataModel();
//                                    tokenDataModel.setRetailer(token);
////                                    HashMap<String, TokenDataModel> stringTokenDataModelHashMap = new HashMap<>();
////                                    stringTokenDataModelHashMap.put("token", tokenDataModel);
//
//                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
//                                            .set(tokenDataModel)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "onSuccess: ");
//                                                }
//                                            });
//                                }
                            }
                        })
                        .

                                addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.getMessage());
                                    }
                                });
//                database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
//                        .update("retailer", token)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "onSuccess: ");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "onFailure: " + e.getMessage());
//                            }
//                        });
            } else if (SharedPrefManager.get_userType().equals("C")) {
                database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.get("customer") != null) {
                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .update("customer", token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                } else if (documentSnapshot.get("retailer") != null) {
                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .update("customer", token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                } else {
                                    TokenDataModel tokenDataModel = new TokenDataModel();
                                    tokenDataModel.setCustomer(token);

                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
                                            .set(tokenDataModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                }
//                                if (documentSnapshot.get("customer") != null) {
////                                    HashMap<String, String> stringTokenDataModelHashMap = (HashMap<String, String>) documentSnapshot.get("token");
////                                    stringTokenDataModelHashMap.put("customer", token);
//                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
//                                            .update("customer", token)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "onSuccess: ");
//                                                }
//                                            });
//                                } else {
//                                    TokenDataModel tokenDataModel = new TokenDataModel();
//                                    tokenDataModel.setCustomer(token);
////                                    HashMap<String, TokenDataModel> stringTokenDataModelHashMap = new HashMap<>();
////                                    stringTokenDataModelHashMap.put("token", tokenDataModel);
//
//                                    database.collection("tokens").document(SharedPrefManager.get_userDocumentID())
//                                            .set(tokenDataModel)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "onSuccess: ");
//                                                }
//                                            });
//                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });
            }
        }
    }
}
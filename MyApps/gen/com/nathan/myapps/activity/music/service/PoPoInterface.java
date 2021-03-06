/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/pole-he/Documents/Android/MyApps/MyApps/src/com/nathan/myapps/activity/music/service/PoPoInterface.aidl
 */
package com.nathan.myapps.activity.music.service;
public interface PoPoInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.nathan.myapps.activity.music.service.PoPoInterface
{
private static final java.lang.String DESCRIPTOR = "com.nathan.myapps.activity.music.service.PoPoInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.nathan.myapps.activity.music.service.PoPoInterface interface,
 * generating a proxy if needed.
 */
public static com.nathan.myapps.activity.music.service.PoPoInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.nathan.myapps.activity.music.service.PoPoInterface))) {
return ((com.nathan.myapps.activity.music.service.PoPoInterface)iin);
}
return new com.nathan.myapps.activity.music.service.PoPoInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_clearPlaylist:
{
data.enforceInterface(DESCRIPTOR);
this.clearPlaylist();
reply.writeNoException();
return true;
}
case TRANSACTION_addSongPlaylist:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.addSongPlaylist(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addSongAllPlaylist:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.addSongAllPlaylist(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_playFile:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.playFile(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isPause:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPause();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPosition:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPosition();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurrentDuration:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrentDuration();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
this.pause();
reply.writeNoException();
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
this.stop();
reply.writeNoException();
return true;
}
case TRANSACTION_skipForward:
{
data.enforceInterface(DESCRIPTOR);
this.skipForward();
reply.writeNoException();
return true;
}
case TRANSACTION_skipBack:
{
data.enforceInterface(DESCRIPTOR);
this.skipBack();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.nathan.myapps.activity.music.service.PoPoInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void clearPlaylist() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearPlaylist, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addSongPlaylist(java.lang.String song) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(song);
mRemote.transact(Stub.TRANSACTION_addSongPlaylist, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addSongAllPlaylist(java.util.List<java.lang.String> song) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(song);
mRemote.transact(Stub.TRANSACTION_addSongAllPlaylist, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void playFile(int position) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(position);
mRemote.transact(Stub.TRANSACTION_playFile, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isPause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPause, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getCurrentDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void skipForward() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_skipForward, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void skipBack() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_skipBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_clearPlaylist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_addSongPlaylist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_addSongAllPlaylist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_playFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_isPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getCurrentDuration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_skipForward = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_skipBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
}
public void clearPlaylist() throws android.os.RemoteException;
public void addSongPlaylist(java.lang.String song) throws android.os.RemoteException;
public void addSongAllPlaylist(java.util.List<java.lang.String> song) throws android.os.RemoteException;
public void playFile(int position) throws android.os.RemoteException;
public boolean isPause() throws android.os.RemoteException;
public int getPosition() throws android.os.RemoteException;
public int getCurrentDuration() throws android.os.RemoteException;
public void pause() throws android.os.RemoteException;
public void stop() throws android.os.RemoteException;
public void skipForward() throws android.os.RemoteException;
public void skipBack() throws android.os.RemoteException;
}

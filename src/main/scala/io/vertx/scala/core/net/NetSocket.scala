/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.scala.core.net

import io.vertx.lang.scala.HandlerOps._
import scala.compat.java8.FunctionConverters._
import scala.collection.JavaConverters._
import io.vertx.scala.core.buffer.Buffer
import io.vertx.scala.core.streams.WriteStream
import io.vertx.scala.core.streams.ReadStream
import io.vertx.core.Handler

/**
  * Represents a socket-like interface to a TCP connection on either the
  * client or the server side.
  * 
  * Instances of this class are created on the client side by an [[io.vertx.scala.core.net.NetClient]]
  * when a connection to a server is made, or on the server side by a [[io.vertx.scala.core.net.NetServer]]
  * when a server accepts a connection.
  * 
  * It implements both  and  so it can be used with
  * [[io.vertx.scala.core.streams.Pump]] to pump data with flow control.
  */
class NetSocket(private val _asJava: io.vertx.core.net.NetSocket) 
    extends io.vertx.scala.core.streams.ReadStream[io.vertx.scala.core.buffer.Buffer] 
    with io.vertx.scala.core.streams.WriteStream[io.vertx.scala.core.buffer.Buffer] {

  def asJava: io.vertx.core.net.NetSocket = _asJava

  /**
    * Same as [[io.vertx.scala.core.net.NetSocket#end]] but writes some data to the stream before ending.
    */
  def end(t: io.vertx.scala.core.buffer.Buffer): Unit = {
    _asJava.end(t.asJava.asInstanceOf[io.vertx.core.buffer.Buffer])
  }

  /**
    * This will return `true` if there are more bytes in the write queue than the value set using [[io.vertx.scala.core.net.NetSocket#setWriteQueueMaxSize]]
    * @return true if write queue is full
    */
  def writeQueueFull(): Boolean = {
    _asJava.writeQueueFull()
  }

  def exceptionHandler(handler: Throwable => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.exceptionHandler(funcToMappedHandler[java.lang.Throwable, Throwable](x => x)(handler))
    this
  }

  def handler(handler: io.vertx.scala.core.buffer.Buffer => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.handler(funcToMappedHandler(Buffer.apply)(handler))
    this
  }

  def pause(): io.vertx.scala.core.net.NetSocket = {
    _asJava.pause()
    this
  }

  def resume(): io.vertx.scala.core.net.NetSocket = {
    _asJava.resume()
    this
  }

  def endHandler(endHandler: () => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.endHandler(funcToMappedHandler[java.lang.Void, Unit](x => x.asInstanceOf[Unit])(_ => endHandler()))
    this
  }

  def write(data: io.vertx.scala.core.buffer.Buffer): io.vertx.scala.core.net.NetSocket = {
    _asJava.write(data.asJava.asInstanceOf[io.vertx.core.buffer.Buffer])
    this
  }

  def setWriteQueueMaxSize(maxSize: Int): io.vertx.scala.core.net.NetSocket = {
    _asJava.setWriteQueueMaxSize(maxSize)
    this
  }

  def drainHandler(handler: () => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.drainHandler(funcToMappedHandler[java.lang.Void, Unit](x => x.asInstanceOf[Unit])(_ => handler()))
    this
  }

  /**
    * When a `NetSocket` is created it automatically registers an event handler with the event bus, the ID of that
    * handler is given by `writeHandlerID`.
    * 
    * Given this ID, a different event loop can send a buffer to that event handler using the event bus and
    * that buffer will be received by this instance in its own event loop and written to the underlying connection. This
    * allows you to write data to other connections which are owned by different event loops.
    * @return the write handler ID
    */
  def writeHandlerID(): String = {
    _asJava.writeHandlerID()
  }

  /**
    * Write a String to the connection, encoded in UTF-8.
    * @param str the string to write
    * @return a reference to this, so the API can be used fluently
    */
  def write(str: String): io.vertx.scala.core.net.NetSocket = {
    _asJava.write(str)
    this
  }

  /**
    * Write a String to the connection, encoded using the encoding `enc`.
    * @param str the string to write
    * @param enc the encoding to use
    * @return a reference to this, so the API can be used fluently
    */
  def write(str: String, enc: String): io.vertx.scala.core.net.NetSocket = {
    _asJava.write(str, enc)
    this
  }

  /**
    * Tell the operating system to stream a file as specified by `filename` directly from disk to the outgoing connection,
    * bypassing userspace altogether (where supported by the underlying operating system. This is a very efficient way to stream files.
    * @param filename file name of the file to send
    * @return a reference to this, so the API can be used fluently
    */
  def sendFile(filename: String): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename)
    this
  }

  /**
    * Tell the operating system to stream a file as specified by `filename` directly from disk to the outgoing connection,
    * bypassing userspace altogether (where supported by the underlying operating system. This is a very efficient way to stream files.
    * @param filename file name of the file to send
    * @param offset offset
    * @return a reference to this, so the API can be used fluently
    */
  def sendFile(filename: String, offset: Long): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename, offset)
    this
  }

  /**
    * Tell the operating system to stream a file as specified by `filename` directly from disk to the outgoing connection,
    * bypassing userspace altogether (where supported by the underlying operating system. This is a very efficient way to stream files.
    * @param filename file name of the file to send
    * @param offset offset
    * @param length length
    * @return a reference to this, so the API can be used fluently
    */
  def sendFile(filename: String, offset: Long, length: Long): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename, offset, length)
    this
  }

  /**
    * Same as [[io.vertx.scala.core.net.NetSocket#sendFile]] but also takes a handler that will be called when the send has completed or
    * a failure has occurred
    * @param filename file name of the file to send
    * @param resultHandler handler
    * @return a reference to this, so the API can be used fluently
    */
  def sendFileWithHandler(filename: String)( resultHandler: io.vertx.core.AsyncResult [Unit] => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename, funcToMappedHandler[io.vertx.core.AsyncResult[java.lang.Void], io.vertx.core.AsyncResult [Unit]](x => io.vertx.lang.scala.AsyncResult[java.lang.Void, Unit](x,(x => ())))(resultHandler))
    this
  }

  /**
    * Same as [[io.vertx.scala.core.net.NetSocket#sendFile]] but also takes a handler that will be called when the send has completed or
    * a failure has occurred
    * @param filename file name of the file to send
    * @param offset offset
    * @param resultHandler handler
    * @return a reference to this, so the API can be used fluently
    */
  def sendFileWithHandler(filename: String, offset: Long)( resultHandler: io.vertx.core.AsyncResult [Unit] => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename, offset, funcToMappedHandler[io.vertx.core.AsyncResult[java.lang.Void], io.vertx.core.AsyncResult [Unit]](x => io.vertx.lang.scala.AsyncResult[java.lang.Void, Unit](x,(x => ())))(resultHandler))
    this
  }

  /**
    * Same as [[io.vertx.scala.core.net.NetSocket#sendFile]] but also takes a handler that will be called when the send has completed or
    * a failure has occurred
    * @param filename file name of the file to send
    * @param offset offset
    * @param length length
    * @param resultHandler handler
    * @return a reference to this, so the API can be used fluently
    */
  def sendFileWithHandler(filename: String, offset: Long, length: Long)( resultHandler: io.vertx.core.AsyncResult [Unit] => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.sendFile(filename, offset, length, funcToMappedHandler[io.vertx.core.AsyncResult[java.lang.Void], io.vertx.core.AsyncResult [Unit]](x => io.vertx.lang.scala.AsyncResult[java.lang.Void, Unit](x,(x => ())))(resultHandler))
    this
  }

  /**
    * @return the remote address for this socket
    */
  def remoteAddress(): io.vertx.scala.core.net.SocketAddress = {
    if(cached_0 == null) {
      cached_0=    SocketAddress.apply(_asJava.remoteAddress())
    }
    cached_0
  }

  /**
    * @return the local address for this socket
    */
  def localAddress(): io.vertx.scala.core.net.SocketAddress = {
    if(cached_1 == null) {
      cached_1=    SocketAddress.apply(_asJava.localAddress())
    }
    cached_1
  }

  /**
    * Calls [[io.vertx.scala.core.net.NetSocket#close]]
    */
  def end(): Unit = {
    _asJava.end()
  }

  /**
    * Close the NetSocket
    */
  def close(): Unit = {
    _asJava.close()
  }

  /**
    * Set a handler that will be called when the NetSocket is closed
    * @param handler the handler
    * @return a reference to this, so the API can be used fluently
    */
  def closeHandler(handler: () => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.closeHandler(funcToMappedHandler[java.lang.Void, Unit](x => x.asInstanceOf[Unit])(_ => handler()))
    this
  }

  /**
    * Upgrade channel to use SSL/TLS. Be aware that for this to work SSL must be configured.
    * @param handler the handler will be notified when it's upgraded
    * @return a reference to this, so the API can be used fluently
    */
  def upgradeToSsl(handler: () => Unit): io.vertx.scala.core.net.NetSocket = {
    _asJava.upgradeToSsl(funcToMappedHandler[java.lang.Void, Unit](x => x.asInstanceOf[Unit])(_ => handler()))
    this
  }

  /**
    * @return true if this [[io.vertx.scala.core.net.NetSocket]] is encrypted via SSL/TLS.
    */
  def isSsl(): Boolean = {
    _asJava.isSsl()
  }

  private var cached_0: io.vertx.scala.core.net.SocketAddress = _
  private var cached_1: io.vertx.scala.core.net.SocketAddress = _
}

object NetSocket {

  def apply(_asJava: io.vertx.core.net.NetSocket): io.vertx.scala.core.net.NetSocket =
    new io.vertx.scala.core.net.NetSocket(_asJava)

}

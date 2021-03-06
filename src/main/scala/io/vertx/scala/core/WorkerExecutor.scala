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

package io.vertx.scala.core

import io.vertx.lang.scala.HandlerOps._
import scala.compat.java8.FunctionConverters._
import scala.collection.JavaConverters._
import io.vertx.scala.core.metrics.Measured
import io.vertx.core.Handler

/**
  * An executor for executing blocking code in Vert.x .
  *
  * It provides the same <code>executeBlocking</code> operation than [[io.vertx.scala.core.Context]] and
  * [[io.vertx.scala.core.Vertx]] but on a separate worker pool.
  */
class WorkerExecutor(private val _asJava: io.vertx.core.WorkerExecutor) 
    extends io.vertx.scala.core.metrics.Measured {

  def asJava: io.vertx.core.WorkerExecutor = _asJava

  /**
    * Whether the metrics are enabled for this measured object
    * @return true if the metrics are enabled
    */
  def isMetricsEnabled(): Boolean = {
    _asJava.isMetricsEnabled()
  }

  /**
    * Safely execute some blocking code.
    * 
    * Executes the blocking code in the handler `blockingCodeHandler` using a thread from the worker pool.
    * 
    * When the code is complete the handler `resultHandler` will be called with the result on the original context
    * (e.g. on the original event loop of the caller).
    * 
    * A `Future` instance is passed into `blockingCodeHandler`. When the blocking code successfully completes,
    * the handler should call the [[io.vertx.scala.core.Future#complete]] or [[io.vertx.scala.core.Future#complete]] method, or the [[io.vertx.scala.core.Future#fail]]
    * method if it failed.
    * 
    * In the `blockingCodeHandler` the current context remains the original context and therefore any task
    * scheduled in the `blockingCodeHandler` will be executed on the this context and not on the worker thread.
    * @param blockingCodeHandler handler representing the blocking code to run
    * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
    * @param resultHandler handler that will be called when the blocking code is complete
    */
  def executeBlockingWithHandler[T](blockingCodeHandler: io.vertx.scala.core.Future[T] => Unit, ordered: Boolean)( resultHandler: io.vertx.core.AsyncResult [T] => Unit): Unit = {
    _asJava.executeBlocking(funcToMappedHandler(Future.apply[T])(blockingCodeHandler), ordered, funcToMappedHandler[io.vertx.core.AsyncResult[T], io.vertx.core.AsyncResult [T]](x => io.vertx.lang.scala.AsyncResult[T, T](x,(x => x)))(resultHandler))
  }

  /**
    * Like [[io.vertx.scala.core.WorkerExecutor#executeBlocking]] called with ordered = true.
    */
  def executeBlockingWithHandler[T](blockingCodeHandler: io.vertx.scala.core.Future[T] => Unit)( resultHandler: io.vertx.core.AsyncResult [T] => Unit): Unit = {
    _asJava.executeBlocking(funcToMappedHandler(Future.apply[T])(blockingCodeHandler), funcToMappedHandler[io.vertx.core.AsyncResult[T], io.vertx.core.AsyncResult [T]](x => io.vertx.lang.scala.AsyncResult[T, T](x,(x => x)))(resultHandler))
  }

  /**
    * Close the executor.
    */
  def close(): Unit = {
    _asJava.close()
  }

}

object WorkerExecutor {

  def apply(_asJava: io.vertx.core.WorkerExecutor): io.vertx.scala.core.WorkerExecutor =
    new io.vertx.scala.core.WorkerExecutor(_asJava)

}

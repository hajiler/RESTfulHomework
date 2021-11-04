// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package SearchRPC

@SerialVersionUID(0L)
final case class LogResponse(
    log: _root_.scala.Option[_root_.scala.Predef.String] = _root_.scala.None,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[LogResponse] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (log.isDefined) {
        val __value = log.get
        __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, __value)
      };
      __size += unknownFields.serializedSize
      __size
    }
    override def serializedSize: _root_.scala.Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      log.foreach { __v =>
        val __m = __v
        _output__.writeString(1, __m)
      };
      unknownFields.writeTo(_output__)
    }
    def getLog: _root_.scala.Predef.String = log.getOrElse("")
    def clearLog: LogResponse = copy(log = _root_.scala.None)
    def withLog(__v: _root_.scala.Predef.String): LogResponse = copy(log = Option(__v))
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => log.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => log.map(_root_.scalapb.descriptors.PString(_)).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = SearchRPC.LogResponse
    // @@protoc_insertion_point(GeneratedMessage[LogResponse])
}

object LogResponse extends scalapb.GeneratedMessageCompanion[SearchRPC.LogResponse] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[SearchRPC.LogResponse] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): SearchRPC.LogResponse = {
    var __log: _root_.scala.Option[_root_.scala.Predef.String] = _root_.scala.None
    var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder = null
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 10 =>
          __log = Option(_input__.readStringRequireUtf8())
        case tag =>
          if (_unknownFields__ == null) {
            _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
          }
          _unknownFields__.parseField(tag, _input__)
      }
    }
    SearchRPC.LogResponse(
        log = __log,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[SearchRPC.LogResponse] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      SearchRPC.LogResponse(
        log = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[_root_.scala.Option[_root_.scala.Predef.String]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SearchRPCProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SearchRPCProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = SearchRPC.LogResponse(
    log = _root_.scala.None
  )
  implicit class LogResponseLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, SearchRPC.LogResponse]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, SearchRPC.LogResponse](_l) {
    def log: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.getLog)((c_, f_) => c_.copy(log = Option(f_)))
    def optionalLog: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[_root_.scala.Predef.String]] = field(_.log)((c_, f_) => c_.copy(log = f_))
  }
  final val LOG_FIELD_NUMBER = 1
  def of(
    log: _root_.scala.Option[_root_.scala.Predef.String]
  ): _root_.SearchRPC.LogResponse = _root_.SearchRPC.LogResponse(
    log
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[LogResponse])
}
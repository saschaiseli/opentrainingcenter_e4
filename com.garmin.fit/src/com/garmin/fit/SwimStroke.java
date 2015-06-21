////////////////////////////////////////////////////////////////////////////////
// The following FIT Protocol software provided may be used with FIT protocol
// devices only and remains the copyrighted property of Dynastream Innovations Inc.
// The software is being provided on an "as-is" basis and as an accommodation,
// and therefore all warranties, representations, or guarantees of any kind
// (whether express, implied or statutory) including, without limitation,
// warranties of merchantability, non-infringement, or fitness for a particular
// purpose, are specifically disclaimed.
//
// Copyright 2014 Dynastream Innovations Inc.
////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 12.10Release
// Tag = $Name$
////////////////////////////////////////////////////////////////////////////////


package com.garmin.fit;

public enum SwimStroke {
   FREESTYLE((short)0),
   BACKSTROKE((short)1),
   BREASTSTROKE((short)2),
   BUTTERFLY((short)3),
   DRILL((short)4),
   MIXED((short)5),
   IM((short)6),
   INVALID((short)255);


   protected short value;




   private SwimStroke(short value) {
     this.value = value;
   }

   public static SwimStroke getByValue(final Short value) {
      for (final SwimStroke type : SwimStroke.values()) {
         if (value == type.value)
            return type;
      }

      return SwimStroke.INVALID;
   }

   public short getValue() {
      return value;
   }


}

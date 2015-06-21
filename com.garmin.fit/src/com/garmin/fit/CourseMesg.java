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


public class CourseMesg extends Mesg {

   protected static final	Mesg courseMesg;
   static {         
      // course   
      courseMesg = new Mesg("course", MesgNum.COURSE);
      courseMesg.addField(new Field("sport", 4, 0, 1, 0, "", false));
      
      courseMesg.addField(new Field("name", 5, 7, 1, 0, "", false));
      
      courseMesg.addField(new Field("capabilities", 6, 140, 1, 0, "", false));
      
   }

   public CourseMesg() {
      super(Factory.createMesg(MesgNum.COURSE));
   }

   public CourseMesg(final Mesg mesg) {
      super(mesg);
   }


   /**
    * Get sport field
    *
    * @return sport
    */
   public Sport getSport() {
      Short value = getFieldShortValue(4, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      if (value == null)
         return null;
      return Sport.getByValue(value);
   }

   /**
    * Set sport field
    *
    * @param sport
    */
   public void setSport(Sport sport) {
      setFieldValue(4, 0, sport.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get name field
    *
    * @return name
    */
   public String getName() {
      return getFieldStringValue(5, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set name field
    *
    * @param name
    */
   public void setName(String name) {
      setFieldValue(5, 0, name, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get capabilities field
    *
    * @return capabilities
    */
   public Long getCapabilities() {
      return getFieldLongValue(6, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set capabilities field
    *
    * @param capabilities
    */
   public void setCapabilities(Long capabilities) {
      setFieldValue(6, 0, capabilities, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

}
